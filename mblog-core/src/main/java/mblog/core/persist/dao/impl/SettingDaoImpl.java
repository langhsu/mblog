/**
 * 
 */
package mblog.core.persist.dao.impl;

import mblog.core.persist.dao.SettingDao;
import mblog.core.persist.entity.SettingPO;
import mtons.modules.persist.impl.DaoImpl;

/**
 * @author langhsu
 *
 */
public class SettingDaoImpl extends DaoImpl<SettingPO> implements SettingDao {
	private static final long serialVersionUID = 1661965983527190778L;

	public SettingDaoImpl() {
		super(SettingPO.class);
	}

	@Override
	public SettingPO findByName(String name) {
		return findUniqueBy("name", name);
	}
	
}
