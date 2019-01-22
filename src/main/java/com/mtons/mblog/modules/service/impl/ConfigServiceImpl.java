/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.entity.Config;
import com.mtons.mblog.modules.repository.ConfigRepository;
import com.mtons.mblog.modules.service.ConfigService;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author langhsu
 *
 */
@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
//    @PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public List<Config> findAll() {
		List<Config> list = configRepository.findAll();
		List<Config> rets = new ArrayList<>();
		
		for (Config po : list) {
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
			Config entity = configRepository.findByKey(st.getKey());

			// 修改
			if (entity != null) {
				entity.setValue(st.getValue());
			}
			// 添加
			else {
				entity = new Config();
				BeanUtils.copyProperties(st, entity);
			}
			configRepository.save(entity);
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
		Config entity = configRepository.findByKey(key);
		if (entity != null) {
			return entity.getValue();
		}
		return null;
	}

	@Override
	@Transactional
	public void initSettings(Resource resource) {
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		session.doWork(connection -> ScriptUtils.executeSqlScript(connection, resource));
	}

}
