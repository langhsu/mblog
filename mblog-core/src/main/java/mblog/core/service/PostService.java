/**
 * 
 */
package mblog.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mblog.core.pojos.Post;
import mtons.modules.pojos.Page;

import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

/**
 * @author langhsu
 *
 */
public interface PostService {
	/**
	 * 分页查询所有文章
	 * @param page
	 */
	void paging(Page page);
	
	/**
	 * 查询个人发布文章
	 * @param page
	 * @param userId
	 */
	void pagingByUserId(Page page, long userId);
	
	/**
	 * 根据关键字搜索
	 * @param page
	 * @param q
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	List<Post> search(Page page, String q) throws InterruptedException, IOException, InvalidTokenOffsetsException;
	
	/**
	 * 搜索 Tag
	 * @param page
	 * @param tag
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	List<Post> searchByTag(Page page, String tag) throws InterruptedException, IOException, InvalidTokenOffsetsException;
	
	/**
	 * 查询最近更新
	 * @param maxResutls
	 * @param ignoreUserId
	 * @return
	 */
	List<Post> findRecents(int maxResutls, long ignoreUserId);
	
	List<Post> findHots(int maxResutls, long ignoreUserId);
	
	/**
	 * 根据Ids查询
	 * @param ids
	 * @return
	 */
	Map<Long, Post> findByIds(Set<Long> ids);
	
	/**
	 * 发布文章
	 * @param post
	 */
	void post(Post post);
	
	/**
	 * 查询单个文章
	 * @param id
	 * @return
	 */
	Post get(long id);
	
	/**
	 * 删除
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 带作者验证的删除
	 * @param id
	 * @param authorId
	 */
	void delete(long id, long authorId);
	
	/**
	 * 自增浏览数
	 * @param id
	 */
	void identityViews(long id);
	
	/**
	 * 自增喜欢数
	 * @param id
	 */
	void identityHearts(long id);
	
	/**
	 * 自增评论数
	 * @param id
	 */
	void identityComments(long id);
}
