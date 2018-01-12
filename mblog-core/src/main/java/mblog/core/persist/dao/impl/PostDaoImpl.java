/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.dao.impl;

import mblog.core.data.Post;
import mblog.core.persist.dao.custom.PostDaoCustom;
import mblog.core.persist.entity.PostPO;
import mblog.core.persist.utils.BeanMapUtils;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author langhsu
 *
 */
public class PostDaoImpl implements PostDaoCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Post> search(Pageable pageable, String q) throws Exception {
		FullTextEntityManager fullTextSession = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

		SearchFactory sf = fullTextSession.getSearchFactory();
		QueryBuilder qb = sf.buildQueryBuilder().forEntity(PostPO.class).get();

		org.apache.lucene.search.Query luceneQuery  = qb.keyword().onFields("title","summary","tags")
				.matching(q).createQuery();

		org.hibernate.search.jpa.FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

	    StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
	    SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style='color:red;'>", "</span>");
        QueryScorer queryScorer = new QueryScorer(luceneQuery);
        Highlighter highlighter = new Highlighter(formatter, queryScorer);
        
		List<PostPO> list = query.getResultList();
	    List<Post> rets = new ArrayList<>(list.size());

	    for (PostPO po : list) {
			Post m = BeanMapUtils.copy(po, 0);

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
		return new PageImpl<>(rets, pageable, query.getResultSize());
	}
	
	@Override
	public Page<Post> searchByTag(Pageable pageable, String tag) {
		FullTextEntityManager fullTextSession = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
		SearchFactory sf = fullTextSession.getSearchFactory();
		QueryBuilder qb = sf.buildQueryBuilder().forEntity(PostPO.class).get();

		org.apache.lucene.search.Query luceneQuery  = null;

		MustJunction term = qb.bool().must(qb.phrase().onField("tags").sentence(tag).createQuery());

		luceneQuery = term.createQuery();

		org.hibernate.search.jpa.FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery);
	    query.setFirstResult(pageable.getOffset());
	    query.setMaxResults(pageable.getPageSize());

		Sort sort = new Sort(new SortField("id", SortField.Type.LONG, true));
		query.setSort(sort);

		List<PostPO> results = query.getResultList();
		List<Post> rets = new ArrayList<>(results.size());

		for (PostPO po : results) {
			Post m = BeanMapUtils.copy(po, 0);
			rets.add(m);
		}

		return new PageImpl<>(rets, pageable, query.getResultSize());
	}

	@Override
	public void resetIndexs() {
		FullTextEntityManager fullTextSession = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
		//异步
		fullTextSession.createIndexer(PostPO.class).start();
	}

}
