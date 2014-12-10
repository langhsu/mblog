package hilo.core.service.impl;

import hilo.core.persist.dao.UserDao;
import hilo.core.persist.entity.UserPO;
import hilo.core.pojos.User;
import hilo.core.service.UserService;

import java.util.Calendar;

import mtons.commons.lang.EntityStatus;
import mtons.commons.pojos.UserProfile;
import mtons.commons.utils.MD5Helper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserProfile login(String username, String password) {
		UserPO po = userDao.get(username);
		UserProfile u = null;
		if (po != null && StringUtils.equals(po.getPassword(), MD5Helper.md5(password))) {
			po.setLastLogin(Calendar.getInstance().getTime());
			u = wrapperProfile(po);
		}
		return u;
	}

	@Override
	@Transactional
	public void register(User user) {
		Assert.notNull(user, "Parameter user can not be null!");
		
		UserPO check = userDao.get(user.getUsername());
		Assert.isNull(check, "Username already exists!");
		
		UserPO po = new UserPO();
		
		BeanUtils.copyProperties(user, po);
		po.setPassword(MD5Helper.md5(user.getPassword()));
		po.setStatus(EntityStatus.ENABLED);
		po.setCreated(Calendar.getInstance().getTime());
		userDao.save(po);
	}

	@Override
	@Transactional
	public UserProfile update(User user) {
		UserPO po = userDao.get(user.getId());
		if (null != po) {
			po.setEmail(user.getEmail());
			po.setNickname(user.getNickname());
		}
		
		return wrapperProfile(po);
	}
	
	@Override
	@Transactional
	public void updatePassword(long id, String newPassword) {
		UserPO po = userDao.get(id);
		if (null != po) {
			po.setPassword(MD5Helper.md5(newPassword));
		}
	}
	
	private UserProfile wrapperProfile(UserPO po) {
		UserProfile profile = new UserProfile(po.getId(), po.getUsername());
		profile.setNickname(po.getNickname());
		profile.setEmail(po.getEmail());
		profile.setLastLogin(po.getLastLogin());
		profile.setStatus(po.getStatus());
		return profile;
	}
	
}
