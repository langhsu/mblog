/**
 * 
 */
package mblog.core.persist.dao;

import mblog.core.persist.entity.SettingPO;
import mtons.modules.persist.Dao;

/**
 * @author langhsu
 *
 */
public interface SettingDao extends Dao<SettingPO> {
	SettingPO findByName(String name);
}
