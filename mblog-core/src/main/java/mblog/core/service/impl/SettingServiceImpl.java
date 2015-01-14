/**
 * 
 */
package mblog.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import mblog.core.persist.dao.SettingDao;
import mblog.core.persist.entity.SettingPO;
import mblog.core.pojos.Setting;
import mblog.core.service.SettingService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author langhsu
 *
 */
public class SettingServiceImpl implements SettingService{
	@Autowired
	private SettingDao settingDao;

	@Override
	@Transactional(readOnly = true)
	public List<Setting> findAll() {
		List<SettingPO> list = settingDao.list();
		List<Setting> rets = new ArrayList<Setting>();
		
		for (SettingPO po : list) {
			Setting r = new Setting();
			BeanUtils.copyProperties(po, r);
			rets.add(r);
		}
		return rets;
	}

	@Override
	@Transactional
	public void update(List<Setting> settings) {
		if (settings == null) {
			return;
		}
		
		for (Setting st :  settings) {
			SettingPO po = settingDao.findByName(st.getName());
			if (po != null) {
				po.setValue(st.getValue());
			}
		}
	}
	
	
}
