/**
 * 
 */
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.persist.entity.AttachPO;
import mtons.modules.persist.Dao;

/**
 * @author langhsu
 *
 */
public interface AttachDao extends Dao<AttachPO> {
	List<AttachPO> list(long toId);
}
