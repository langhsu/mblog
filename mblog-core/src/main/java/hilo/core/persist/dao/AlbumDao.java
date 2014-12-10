/**
 * 
 */
package hilo.core.persist.dao;

import hilo.core.persist.entity.AlbumPO;

import java.util.List;

import mtons.commons.persist.Dao;

/**
 * @author langhsu
 *
 */
public interface AlbumDao extends Dao<AlbumPO> {
	List<AlbumPO> list(int projectId, long toId);
}
