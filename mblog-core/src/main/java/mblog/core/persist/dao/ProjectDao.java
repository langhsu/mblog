/**
 * 
 */
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.persist.entity.ProjectPO;
import mtons.commons.persist.Dao;

/**
 * @author langhsu
 *
 */
public interface ProjectDao extends Dao<ProjectPO> {
	List<ProjectPO> find(int level);
}
