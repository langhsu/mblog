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

import mblog.core.data.Comment;
import mblog.core.persist.entity.CommentPO;
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
	Page<Comment> paging4Admin(Pageable pageable);

	Page<Comment> paging4Home(Pageable pageable, long authorId);

	/**
	 * 查询评论列表
	 * @param pageable
	 * @param toId
	 */
	Page<Comment> paging(Pageable pageable, long toId);

	Map<Long, Comment> findByIds(Set<Long> ids);
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	long post(Comment comment);
	
	void delete(List<Long> ids);

	/**
	 * 带作者验证的删除
	 * @param id
	 * @param authorId
	 */
	void delete(long id, long authorId);

	List<CommentPO> findAllByAuthorIdAndToId(long authorId, long toId);
}
