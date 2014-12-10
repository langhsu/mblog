/**
 * 
 */
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.persist.entity.AlbumPO;
import mtons.commons.persist.Dao;

/**
 * @author langhsu
 *
 */
public interface AlbumDao extends Dao<AlbumPO> {
	List<AlbumPO> list(int projectId, long toId);
}
