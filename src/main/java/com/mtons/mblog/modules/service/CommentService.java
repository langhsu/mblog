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

	Page<CommentVO> paging4Home(Pageable pageable, long authorId);

	/**
	 * 查询评论列表
	 * @param pageable
	 * @param toId
	 */
	Page<CommentVO> paging(Pageable pageable, long toId);

	Map<Long, CommentVO> findByIds(Set<Long> ids);
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	long post(CommentVO comment);
	
	void delete(List<Long> ids);

	/**
	 * 带作者验证的删除
	 * @param id
	 * @param authorId
	 */
	void delete(long id, long authorId);

	List<Comment> findAllByAuthorIdAndToId(long authorId, long toId);

	List<CommentVO> latests(int maxResults);
}
