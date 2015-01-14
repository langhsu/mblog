/**
 * 
 */
package mblog.core.service;

import mblog.core.pojos.Comment;
import mtons.modules.pojos.Page;

/**
 * @author langhsu
 *
 */
public interface CommentService {
	/**
	 * 查询评论列表
	 * @param page
	 * @param toId
	 */
	void paging(Page page, long toId);
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	long post(Comment comment);
}
