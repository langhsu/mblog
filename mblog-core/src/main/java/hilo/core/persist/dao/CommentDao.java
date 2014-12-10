/**
 * 
 */
package hilo.core.persist.dao;

import hilo.core.persist.entity.CommentPO;

import java.util.List;

import mtons.commons.persist.Dao;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface CommentDao extends Dao<CommentPO> {
	List<CommentPO> paging(Paging paging, int projectId, long contentId);
}
