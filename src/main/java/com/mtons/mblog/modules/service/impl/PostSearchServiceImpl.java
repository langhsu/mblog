package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.Post;
import com.mtons.mblog.modules.service.PostSearchService;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.modules.utils.BeanMapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.MustJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author : langhsu
 * @version : 1.0
 * @date : 2019/1/18
 */
@Service
@Transactional
public class PostSearchServiceImpl implements PostSearchService {
    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Override
    public Page<PostVO> search(Pageable pageable, String q) throws Exception {
        FullTextEntityManager fullTextSession = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

        SearchFactory sf = fullTextSession.getSearchFactory();
        QueryBuilder qb = sf.buildQueryBuilder().forEntity(Post.class).get();

        org.apache.lucene.search.Query luceneQuery  = qb.keyword().onFields("title","summary","tags")
                .matching(q).createQuery();

        org.hibernate.search.jpa.FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style='color:red;'>", "</span>");
        QueryScorer queryScorer = new QueryScorer(luceneQuery);
        Highlighter highlighter = new Highlighter(formatter, queryScorer);

        List<Post> list = query.getResultList();
        List<PostVO> rets = new ArrayList<>(list.size());

        for (Post po : list) {
            PostVO m = BeanMapUtils.copy(po, 0);

            // 处理高亮
            String title = highlighter.getBestFragment(standardAnalyzer, "title", m.getTitle());
            String summary = highlighter.getBestFragment(standardAnalyzer, "summary", m.getSummary());

            if (StringUtils.isNotEmpty(title)) {
                m.setTitle(title);
            }
            if (StringUtils.isNotEmpty(summary)) {
                m.setSummary(summary);
            }
            rets.add(m);
        }
        buildUsers(rets);
        return new PageImpl<>(rets, pageable, query.getResultSize());
    }

    @Override
    public Page<PostVO> searchByTag(Pageable pageable, String tag) {
        FullTextEntityManager fullTextSession = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        SearchFactory sf = fullTextSession.getSearchFactory();
        QueryBuilder qb = sf.buildQueryBuilder().forEntity(Post.class).get();

        org.apache.lucene.search.Query luceneQuery  = null;

        MustJunction term = qb.bool().must(qb.phrase().onField("tags").sentence(tag).createQuery());

        luceneQuery = term.createQuery();

        org.hibernate.search.jpa.FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        Sort sort = new Sort(new SortField("id", SortField.Type.LONG, true));
        query.setSort(sort);

        List<Post> results = query.getResultList();
        List<PostVO> rets = new ArrayList<>(results.size());

        for (Post po : results) {
            PostVO m = BeanMapUtils.copy(po, 0);
            rets.add(m);
        }

        buildUsers(rets);
        return new PageImpl<>(rets, pageable, query.getResultSize());
    }

    @Override
    public void resetIndexs() {
        FullTextEntityManager fullTextSession = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        //异步
        fullTextSession.createIndexer(Post.class).start();
    }

    private void buildUsers(List<PostVO> list) {
        if (null == list) {
            return;
        }
        HashSet<Long> uids = new HashSet<>();
        list.forEach(n -> uids.add(n.getAuthorId()));
        Map<Long, UserVO> userMap = userService.findMapByIds(uids);
        list.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
    }
}
