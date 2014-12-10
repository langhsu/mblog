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
	void paging(Paging paging, int projectId, long contentId);
	long post(Comment comment);
}
