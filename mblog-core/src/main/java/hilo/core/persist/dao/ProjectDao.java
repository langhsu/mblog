/**
 * 
 */
package hilo.core.persist.dao;

import hilo.core.persist.entity.ProjectPO;

import java.util.List;

import mtons.commons.persist.Dao;

/**
 * @author langhsu
 *
 */
public interface ProjectDao extends Dao<ProjectPO> {
	List<ProjectPO> find(int level);
}
