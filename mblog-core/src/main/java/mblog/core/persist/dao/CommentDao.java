/**
 * 
 */
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.persist.entity.CommentPO;
import mtons.commons.persist.Dao;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface CommentDao extends Dao<CommentPO> {
	List<CommentPO> paging(Paging paging, int projectId, long contentId);
}
