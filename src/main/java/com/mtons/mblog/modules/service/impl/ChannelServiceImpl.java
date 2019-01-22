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

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.repository.ChannelRepository;
import com.mtons.mblog.modules.service.ChannelService;
import com.mtons.mblog.modules.entity.Channel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author langhsu
 *
 */
@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService {
	@Autowired
	private ChannelRepository channelRepository;

	@Override
	public List<Channel> findAll(int status) {
		List<Channel> list;
		if (status > Consts.IGNORE) {
			list = channelRepository.findAllByStatus(status);
		} else {
			list = channelRepository.findAll();
		}
		return list;
	}

	@Override
	public Map<Integer, Channel> findMapByIds(Collection<Integer> ids) {
		List<Channel> list = channelRepository.findAllByIdIn(ids);
		Map<Integer, Channel> rets = new HashMap<>();
		list.forEach(po -> rets.put(po.getId(), po));
		return rets;
	}

	@Override
	public Channel getById(int id) {
		return channelRepository.findOne(id);
	}

	@Override
	@Transactional
	public void update(Channel channel) {
		Channel po = channelRepository.findOne(channel.getId());
		if (po != null) {
			BeanUtils.copyProperties(channel, po);
		} else {
			po = new Channel();
			BeanUtils.copyProperties(channel, po);
		}
		channelRepository.save(po);
	}

	@Override
	@Transactional
	public void delete(int id) {
		channelRepository.delete(id);
	}

}
