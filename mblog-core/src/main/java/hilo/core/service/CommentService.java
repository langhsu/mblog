/**
 * 
 */
package hilo.core.service;

import hilo.core.pojos.Comment;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface CommentService {
	void paging(Paging paging, int projectId, long contentId);
	long post(Comment comment);
}
