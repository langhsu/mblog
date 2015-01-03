package mblog.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mblog.core.persist.dao.UserDao;
import mblog.core.persist.entity.UserPO;
import mblog.core.pojos.User;
import mblog.core.service.UserService;
import mblog.core.utils.BeanMapUtils;
import mtons.modules.exception.MtonsException;
import mtons.modules.lang.Const;
import mtons.modules.lang.EntityStatus;
import mtons.modules.pojos.Page;
import mtons.modules.pojos.UserProfile;
import mtons.modules.utils.MD5Helper;

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
		if (po != null) {
			if (po.getStatus() == Const.STATUS_CLOSED) {
				throw new MtonsException("您的账户已被封禁");
			}
			if (StringUtils.equals(po.getPassword(), MD5Helper.md5(password))) {
				po.setLastLogin(Calendar.getInstance().getTime());
				u = wrapperProfile(po);
			}
		}
		return u;
	}

	@Override
	@Transactional
	public void register(User user) {
		Assert.notNull(user, "Parameter user can not be null!");
		
		Assert.hasLength(user.getUsername(), "用户名不能为空!");
		Assert.hasLength(user.getPassword(), "密码不能为空!");
		
		UserPO check = userDao.get(user.getUsername());
		Assert.isNull(check, "用户名已经存在!");
		
		UserPO po = new UserPO();
		
		BeanUtils.copyProperties(user, po);
		
		Date current = Calendar.getInstance().getTime();
		po.setPassword(MD5Helper.md5(user.getPassword()));
		po.setStatus(EntityStatus.ENABLED);
		po.setCreated(current);
		po.setUpdated(current);
		userDao.save(po);
	}

	@Override
	@Transactional
	public UserProfile update(User user) {
		UserPO po = userDao.get(user.getId());
		if (null != po) {
			po.setEmail(user.getEmail());
			po.setName(user.getName());
		}
		
		return wrapperProfile(po);
	}
	
	@Override
	@Transactional
	public User get(long id) {
		UserPO po = userDao.get(id);
		User ret = null;
		if (po != null) {
			ret = BeanMapUtils.copy(po);
		}
		return ret;
	}
	
	@Override
	@Transactional
	public UserProfile updateAvater(long id, String path) {
		UserPO po = userDao.get(id);
		if (po != null) {
			po.setAvater(path);
			po.setUpdated(new Date());
		}
		return wrapperProfile(po);
	}
	
	@Override
	@Transactional
	public void updatePassword(long id, String newPassword) {
		UserPO po = userDao.get(id);
		
		Assert.hasLength(newPassword, "密码不能为空!");
		
		if (null != po) {
			po.setPassword(MD5Helper.md5(newPassword));
		}
	}
	
	@Override
	@Transactional
	public void updatePassword(long id, String oldPassword, String newPassword) {
		UserPO po = userDao.get(id);
		
		Assert.hasLength(newPassword, "密码不能为空!");
		
		if (po != null) {
			Assert.isTrue(MD5Helper.md5(oldPassword).equals(po.getPassword()), "当前密码不正确");
			po.setPassword(MD5Helper.md5(newPassword));
		}
	}
	
	@Override
	@Transactional
	public void updateStatus(long id, int status) {
		UserPO po = userDao.get(id);
		
		if (po != null) {
			po.setStatus(status);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public void paging(Page page) {
		List<UserPO> list = userDao.paging(page);
		List<User> rets = new ArrayList<User>();
		
		for (UserPO po : list) {
			User u = BeanMapUtils.copy(po);
			rets.add(u);
		}
		page.setResults(rets);
	}
	
	private UserProfile wrapperProfile(UserPO po) {
		UserProfile profile = new UserProfile(po.getId(), po.getUsername());
		profile.setName(po.getName());
		profile.setEmail(po.getEmail());
		profile.setAvater(po.getAvater());
		profile.setLastLogin(po.getLastLogin());
		profile.setStatus(po.getStatus());
		return profile;
	}

}
