/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mblog.base.context.AppContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mblog.core.data.Config;
import mblog.core.persist.dao.ConfigDao;
import mblog.core.persist.entity.ConfigPO;
import mblog.core.persist.service.ConfigService;

/**
 * @author langhsu
 *
 */
@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigDao configDao;

	@Autowired
	private AppContext appContext;

	@Override
	@Transactional(readOnly = true)
	public List<Config> findAll() {
		List<ConfigPO> list = configDao.findAll();
		List<Config> rets = new ArrayList<>();
		
		for (ConfigPO po : list) {
			Config r = new Config();
			BeanUtils.copyProperties(po, r);
			rets.add(r);
		}
		return rets;
	}

	@Override
	@Transactional
	public void update(List<Config> settings) {
		if (settings == null) {
			return;
		}
		
		for (Config st :  settings) {
			ConfigPO entity = configDao.findByKey(st.getKey());

			// 修改
			if (entity != null) {
				entity.setValue(st.getValue());
			}
			// 添加
			else {
				entity = new ConfigPO();
				BeanUtils.copyProperties(st, entity);
			}
			configDao.save(entity);
			appContext.getConfig().put(entity.getKey(), entity.getValue()); //更新全局变量
			appContext.getServletContext().setAttribute(entity.getKey(),entity.getValue()); //更新容器全局变量
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Config> findAll2Map() {
		List<Config> list = findAll();
		Map<String, Config> ret = new LinkedHashMap<String, Config>();
		
		for (Config c : list) {
			ret.put(c.getKey(), c);
		}
		return ret;
	}

	public String findConfigValueByName(String key) {
		ConfigPO entity = configDao.findByKey(key);
		if (entity != null) {
			return entity.getValue();
		}
		return null;
	}
	
}
