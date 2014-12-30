/**
 * 
 */
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.persist.entity.CommentPO;
import mtons.modules.persist.Dao;
import mtons.modules.pojos.Page;

/**
 * @author langhsu
 *
 */
public interface CommentDao extends Dao<CommentPO> {
	List<CommentPO> paging(Page page, long toId);
}
