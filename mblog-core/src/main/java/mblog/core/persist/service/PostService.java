/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.service;

import mblog.core.data.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文章管理
 * @author langhsu
 *
 */
public interface PostService {
	/**
	 * 分页查询所有文章
	 * 
	 * @param pageable
	 * @param channelId 分组Id
	 * @param ord   排序
	 */
	Page<Post> paging(Pageable pageable, int channelId, String ord);

	Page<Post> paging4Admin(Pageable pageable, long id, String title, int channelId);
	
	/**
	 * 查询个人发布文章
	 * @param pageable
	 * @param userId
	 */
	Page<Post> pagingByAuthorId(Pageable pageable, long userId);

	List<Post> findAllFeatured();
	
	/**
	 * 根据关键字搜索
	 * @param pageable
	 * @param q
	 * @throws Exception 
	 */
	Page<Post> search(Pageable pageable, String q) throws Exception;
	
	/**
	 * 搜索 Tag
	 * @param pageable
	 * @param tag
	 */
	Page<Post> searchByTag(Pageable pageable, String tag);
	
	/**
	 * 查询最近更新 - 按发布时间排序
	 * @param maxResults
	 * @param ignoreUserId
	 * @return
	 */
	List<Post> findLatests(int maxResults, long ignoreUserId);

	/**
	 * 查询热门文章 - 按浏览次数排序
	 * @param maxResults
	 * @param ignoreUserId
	 * @return
	 */
	List<Post> findHots(int maxResults, long ignoreUserId);
	
	/**
	 * 根据Ids查询
	 * @param ids
	 * @return <id, 文章对象>
	 */
	Map<Long, Post> findMapByIds(Set<Long> ids);

	/**
	 * 发布文章
	 * @param post
	 */
	long post(Post post);
	
	/**
	 * 文章详情
	 * @param id
	 * @return
	 */
	Post get(long id);

	/**
	 * 更新文章方法
	 * @param p
	 */
	void update(Post p);

	/**
	 * 推荐/精华
	 * @param id
	 * @param featured 0: 取消, 1: 加精
	 */
	void updateFeatured(long id, int featured);

	/**
	 * 置顶
	 * @param id
	 * @param weight 0: 取消, 1: 置顶
	 */
	void updateWeight(long id, int weight);
	
	/**
	 * 删除
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 带作者验证的删除 - 验证是否属于自己的文章
	 * @param id
	 * @param authorId
	 */
	void delete(long id, long authorId);

	/**
	 * 批量删除文章, 且刷新缓存
	 *
	 * @param ids
	 */
	void delete(Collection<Long> ids);
	
	/**
	 * 自增浏览数
	 * @param id
	 */
	void identityViews(long id);
	
	/**
	 * 自增评论数
	 * @param id
	 */
	void identityComments(long id);

	/**
	 * 喜欢文章
	 * @param userId
	 * @param postId
	 */
	void favor(long userId, long postId);

	/**
	 * 取消喜欢文章
	 * @param userId
	 * @param postId
	 */
	void unfavor(long userId, long postId);


	void resetIndexs();

}
