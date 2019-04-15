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

import com.mtons.mblog.modules.data.CommentVO;
import com.mtons.mblog.modules.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author langhsu
 *
 */
public interface CommentService {
	Page<CommentVO> paging4Admin(Pageable pageable);

	Page<CommentVO> pagingByAuthorId(Pageable pageable, long authorId);

	/**
	 * 查询评论列表
	 * @param pageable
	 * @param postId
	 */
	Page<CommentVO> pagingByPostId(Pageable pageable, long postId);

	List<CommentVO> findLatestComments(int maxResults);

	Map<Long, CommentVO> findByIds(Set<Long> ids);

	Comment findById(long id);
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	long post(CommentVO comment);
	
	void delete(List<Long> ids);

	void delete(long id, long authorId);

	void deleteByPostId(long postId);

	long count();

	long countByAuthorIdAndPostId(long authorId, long postId);
}
