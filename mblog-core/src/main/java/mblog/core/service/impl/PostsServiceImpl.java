/**
 * 
 */
package mblog.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mblog.core.persist.dao.AttachDao;
import mblog.core.persist.dao.PostsDao;
import mblog.core.persist.dao.UserDao;
import mblog.core.persist.entity.PostsPO;
import mblog.core.pojos.Attach;
import mblog.core.pojos.Posts;
import mblog.core.pojos.User;
import mblog.core.service.AttachService;
import mblog.core.service.PostsService;
import mtons.commons.lang.EntityStatus;
import mtons.commons.pojos.Paging;
import mtons.commons.pojos.UserContextHolder;
import mtons.commons.pojos.UserProfile;
import mtons.commons.utils.PreviewHtmlUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author langhsu
 *
 */
public class PostsServiceImpl implements PostsService {
	@Autowired
	private PostsDao postsDao;
	@Autowired
	private AttachDao attachDao;
	@Autowired
	private AttachService attachService;
	@Autowired
	private UserDao userDao;
	
	private static String[] IGNORE = new String[]{"author", "snapshot"};
	private static String[] IGNORE_LIST = new String[]{"author", "snapshot", "content"};
	
	@Override
	@Transactional(readOnly = true)
	public void paging(Paging paging) {
		List<PostsPO> list = postsDao.paging(paging);
		List<Posts> rets = new ArrayList<Posts>();
		for (PostsPO po : list) {
			rets.add(toVo(po, 0));
		}
		paging.setResults(rets);
	}
	
	@Override
	@Transactional(readOnly = true)
	public void pagingByUserId(Paging paging, long userId) {
		List<PostsPO> list = postsDao.pagingByUserId(paging, userId);
		List<Posts> rets = new ArrayList<Posts>();
		for (PostsPO po : list) {
			rets.add(toVo(po ,0));
		}
		paging.setResults(rets);
	}
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Posts> search(Paging paging, String q) throws InterruptedException, IOException, InvalidTokenOffsetsException {
		FullTextSession fullTextSession = Search.getFullTextSession(postsDao.getSession());
//	    fullTextSession.createIndexer().startAndWait();
	    SearchFactory sf = fullTextSession.getSearchFactory();
	    QueryBuilder qb = sf.buildQueryBuilder().forEntity(PostsPO.class).get();
	    org.apache.lucene.search.Query luceneQuery  = qb.keyword().onFields("title","summary","tags").matching(q).createQuery();
	    FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery);
	    query.setFirstResult(paging.getFirstResult());
	    query.setMaxResults(paging.getMaxResults());
	   
	    StandardAnalyzer standardAnalyzer = new StandardAnalyzer(); 
	    SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style='color:red;'>", "</span>");
        QueryScorer queryScorer = new QueryScorer(luceneQuery);
        Highlighter highlighter = new Highlighter(formatter, queryScorer);
        
	    List<PostsPO> list = query.list();
	    int resultSize = query.getResultSize();
	    
	    List<Posts> rets = new ArrayList<Posts>();
		for (PostsPO po : list) {
			Posts m = toVo(po ,0);
			String title = highlighter.getBestFragment(standardAnalyzer, "title", m.getTitle());
			String summary = highlighter.getBestFragment(standardAnalyzer, "summary", m.getSummary());
			String tags = highlighter.getBestFragment(standardAnalyzer, "tags", m.getTags());
			if (StringUtils.isNotEmpty(title)) {
				m.setTitle(title);
			}
			if (StringUtils.isNotEmpty(summary)) {
				m.setSummary(summary);
			}
			if (StringUtils.isNotEmpty(tags)) {
				m.setTags(tags);
			}
			rets.add(m);
		}
		paging.setTotalCount(resultSize);
		paging.setResults(rets);
		return rets;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Posts> recents(int maxResutls, long ignoreUserId) {
		List<PostsPO> list = postsDao.recents(maxResutls, ignoreUserId);
		List<Posts> rets = new ArrayList<Posts>();
		for (PostsPO po : list) {
			rets.add(toVo(po, 0));
		}
		return rets;
	}
	
	@Override
	@Transactional
	public void add(Posts post) {
		PostsPO po = postsDao.get(post.getId());
		if (po != null) {
			po.setUpdated(new Date());
			// po.setProject(projectDao.get(art.getProjectId()));
			po.setTitle(post.getTitle());
			po.setContent(post.getContent());
			po.setSummary(trimSummary(post.getContent()));
			po.setTags(post.getTags());
		} else {
			po = new PostsPO();
			UserProfile up = UserContextHolder.getUserProfile();
			
			po.setAuthor(userDao.get(up.getId()));
			po.setCreated(new Date());
			po.setStatus(EntityStatus.ENABLED);
			
			// content
			po.setType(post.getType());
			po.setTitle(post.getTitle());
			po.setContent(post.getContent());
			po.setSummary(trimSummary(post.getContent())); // summary handle
			po.setTags(post.getTags());
			
			postsDao.save(po);
		}
		
		// album handle
		if (post.getAlbums() != null) {
			for (int i = 0; i < post.getAlbums().size(); i++) {
				Attach a = post.getAlbums().get(i);
				a.setToId(po.getId());
				long id = attachService.add(a);
				if (i == 0) {
					po.setSnapshot(attachDao.get(id));
				}
			}
		}
	}
	
	@Override
	@Transactional
	public Posts get(long id) {
		PostsPO po = postsDao.get(id);
		Posts d = null;
		if (po != null) {
			d = toVo(po, 1);
		}
		List<Attach> albs = attachService.list(d.getId());
		d.setAlbums(albs);
		return d;
	}
	
	@Override
	@Transactional
	public void delete(long id) {
		UserProfile up = UserContextHolder.getUserProfile();
		
		Assert.notNull(up, "用户认证失败, 请重新登录!");
		
		PostsPO po = postsDao.get(id);
		if (po != null) {
			Assert.isTrue(po.getAuthor().getId() == up.getId(), "认证失败");
			attachService.deleteByToId(id);
			postsDao.delete(po);
		}
	}
	
	private Posts toVo(PostsPO po, int level) {
		Posts d = new Posts();
		if (level > 0) {
			BeanUtils.copyProperties(po, d, IGNORE);
		} else {
			BeanUtils.copyProperties(po, d, IGNORE_LIST);
		}
		
		if (po.getAuthor() != null) {
			User u = new User();
			u.setId(po.getAuthor().getId());
			u.setUsername(po.getAuthor().getUsername());
			u.setName(po.getAuthor().getName());
			u.setAvater(po.getAuthor().getAvater());
			d.setAuthor(u);
		}
		if (po.getSnapshot() != null) {
			Attach a = new Attach();
			BeanUtils.copyProperties(po.getSnapshot(), a);
			d.setSnapshot(a);
		}
		return d;
	}
	
	/**
     * 截取文章内容
     * @param text
     * @return
     */
    private String trimSummary(String text){
        return PreviewHtmlUtils.truncateHTML(text, 126);
    }

}
