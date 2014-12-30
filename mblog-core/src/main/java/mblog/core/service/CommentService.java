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
	void paging(Page page, long toId);
	long post(Comment comment);
}
