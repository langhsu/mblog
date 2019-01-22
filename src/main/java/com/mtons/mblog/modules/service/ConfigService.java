/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.entity.Config;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;


/**
 * @author langhsu
 *
 */
public interface ConfigService {
	/**
	 * 查询所有配置
	 * @return list
	 */
	List<Config> findAll();

	/**
	 * 查询所有配置
	 * @return map
	 */
	Map<String, Config> findAll2Map();

	/**
	 * 添加或修改配置
	 * - 修改时根据key判断唯一性
	 * @param configs
	 */
	void update(List<Config> configs);

	/**
	 * 根据key查找相应的值
	 *
	 * @param key
	 * @return
	 */
	String findConfigValueByName(String key);

	void initSettings(Resource resource);
}
