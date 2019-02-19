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

import com.mtons.mblog.modules.entity.Options;
import com.mtons.mblog.modules.repository.OptionsRepository;
import com.mtons.mblog.modules.service.OptionsService;
import org.apache.commons.lang3.StringUtils;
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
 */
@Service
public class OptionsServiceImpl implements OptionsService {
	@Autowired
	private OptionsRepository optionsRepository;
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public List<Options> findAll() {
		List<Options> list = optionsRepository.findAll();
		List<Options> rets = new ArrayList<>();
		
		for (Options po : list) {
			Options r = new Options();
			BeanUtils.copyProperties(po, r);
			rets.add(r);
		}
		return rets;
	}

	@Override
	@Transactional
	public void update(List<Options> options) {
		if (options == null) {
			return;
		}

		options.forEach(opt -> {
			Options entity = optionsRepository.findByKey(opt.getKey());

			if (entity != null) {
				entity.setValue(StringUtils.trim(opt.getValue()));
			} else {
				entity = new Options();
				entity.setKey(opt.getKey());
				entity.setValue(StringUtils.trim(opt.getValue()));
			}
			optionsRepository.save(entity);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Options> findAll2Map() {
		List<Options> list = findAll();
		Map<String, Options> ret = new LinkedHashMap<>();
		list.forEach(opt -> ret.put(opt.getKey(), opt));
		return ret;
	}

	public String findConfigValueByName(String key) {
		Options entity = optionsRepository.findByKey(key);
		if (entity != null) {
			return entity.getValue();
		}
		return null;
	}

	@Override
	@Transactional
	public void initSettings(Resource resource) {
		Session session = entityManager.unwrap(Session.class);
		session.doWork(connection -> ScriptUtils.executeSqlScript(connection, resource));
	}

}
