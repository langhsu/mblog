/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.PostVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = Consts.CACHE_USER)
public interface PostService {
	/**
	 * 分页查询所有文章
	 * 
	 * @param pageable
	 * @param channelId 分组Id
	 */
	@Cacheable
	Page<PostVO> paging(Pageable pageable, int channelId, Set<Integer> excludeChannelIds);

	Page<PostVO> paging4Admin(Pageable pageable, int channelId, String title);
	
	/**
	 * 查询个人发布文章
	 * @param pageable
	 * @param userId
	 */
	@Cacheable
	Page<PostVO> pagingByAuthorId(Pageable pageable, long userId);

	/**
	 * 查询最近更新 - 按发布时间排序
	 * @param maxResults
	 * @return
	 */
	@Cacheable(key = "'latest_' + #maxResults")
	List<PostVO> findLatestPosts(int maxResults);

	/**
	 * 查询热门文章 - 按浏览次数排序
	 * @param maxResults
	 * @return
	 */
	@Cacheable(key = "'hottest_' + #maxResults")
	List<PostVO> findHottestPosts(int maxResults);
	
	/**
	 * 根据Ids查询
	 * @param ids
	 * @return <id, 文章对象>
	 */
	Map<Long, PostVO> findMapByIds(Set<Long> ids);

	/**
	 * 发布文章
	 * @param post
	 */
	@CacheEvict(allEntries = true)
	long post(PostVO post);
	
	/**
	 * 文章详情
	 * @param id
	 * @return
	 */
	@Cacheable(key = "'post_' + #id")
	PostVO get(long id);

	/**
	 * 更新文章方法
	 * @param p
	 */
	@CacheEvict(allEntries = true)
	void update(PostVO p);

	/**
	 * 推荐/精华
	 * @param id
	 * @param featured 0: 取消, 1: 加精
	 */
	@CacheEvict(allEntries = true)
	void updateFeatured(long id, int featured);

	/**
	 * 置顶
	 * @param id
	 * @param weighted 0: 取消, 1: 置顶
	 */
	@CacheEvict(allEntries = true)
	void updateWeight(long id, int weighted);
	
	/**
	 * 带作者验证的删除 - 验证是否属于自己的文章
	 * @param id
	 * @param authorId
	 */
	@CacheEvict(allEntries = true)
	void delete(long id, long authorId);

	/**
	 * 批量删除文章, 且刷新缓存
	 *
	 * @param ids
	 */
	@CacheEvict(allEntries = true)
	void delete(Collection<Long> ids);
	
	/**
	 * 自增浏览数
	 * @param id
	 */
	@CacheEvict(key = "'view_' + #id")
	void identityViews(long id);
	
	/**
	 * 自增评论数
	 * @param id
	 */
	@CacheEvict(key = "'view_' + #id")
	void identityComments(long id);

	/**
	 * 喜欢文章
	 * @param userId
	 * @param postId
	 */
	@CacheEvict(key = "'view_' + #postId")
	void favor(long userId, long postId);

	/**
	 * 取消喜欢文章
	 * @param userId
	 * @param postId
	 */
	@CacheEvict(key = "'view_' + #postId")
	void unfavor(long userId, long postId);

	long count();
}
