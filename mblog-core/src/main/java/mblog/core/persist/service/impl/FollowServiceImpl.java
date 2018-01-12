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

import mblog.base.lang.MtonsException;
import mblog.core.data.User;
import mblog.core.persist.dao.FollowDao;
import mblog.core.persist.entity.FollowPO;
import mblog.core.persist.entity.UserPO;
import mblog.core.persist.service.FollowService;
import mblog.core.persist.service.UserEventService;
import mblog.core.persist.utils.BeanMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author langhsu
 *
 */
@Service
public class FollowServiceImpl implements FollowService {
	@Autowired
	private FollowDao followDao;
	@Autowired
	private UserEventService userEventService;

	@Override
	@Transactional
	public long follow(long userId, long followId) {
		long ret = 0;

		Assert.state(userId != followId, "您不能关注自己");

		FollowPO po = followDao.findByUserAndFollow(new UserPO(userId), new UserPO(followId));

		if (po == null) {
			po = new FollowPO();
			po.setUser(new UserPO(userId));
			po.setFollow(new UserPO(followId));
			po.setCreated(new Date());

			followDao.save(po);

			ret = po.getId();

			userEventService.identityFollow(userId, followId, true);
			userEventService.identityFans(followId, userId, true);
		} else {
			throw new MtonsException("您已经关注过此用户了");
		}
		return ret;
	}

	@Override
	@Transactional
	public void unfollow(long userId, long followId) {
		int ret = followDao.deleteByUserAndFollow(new UserPO(userId), new UserPO(followId));

		if (ret <= 0) {
			throw new MtonsException("取消关注失败");
		} else {
			userEventService.identityFollow(userId, followId, false);
			userEventService.identityFans(followId, userId, false);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> follows(Pageable pageable, long userId) {
		Page<FollowPO> page = followDao.findAllByFollow(pageable, new UserPO(userId));
		List<User> rets = new ArrayList<>();

		for (FollowPO po : page.getContent()) {
			rets.add(BeanMapUtils.copy(po.getFollow(), 0));
		}
		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> fans(Pageable pageable, long userId) {
		Page<FollowPO> page = followDao.findAllByUser(pageable, new UserPO(userId));
		List<User> rets = new ArrayList<>();

		for (FollowPO po : page.getContent()) {
			rets.add(BeanMapUtils.copy(po.getUser(), 0));
		}

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	@Transactional
	public boolean checkFollow(long userId, long followId) {
		return (followDao.findByUserAndFollow(new UserPO(userId), new UserPO(followId)) != null);
	}

	@Override
	@Transactional
	public boolean checkCrossFollow(long userId, long targetUserId) {
		List<FollowPO> list = followDao.findAllCrossFollow(userId, targetUserId);
		return  list != null && list.size() > 0;
	}

}
