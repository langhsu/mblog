/**
 * 
 */
package mblog.core.service;

import mblog.core.pojos.Comment;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface CommentService {
	void paging(Paging paging, long toId);
	long post(Comment comment);
}
