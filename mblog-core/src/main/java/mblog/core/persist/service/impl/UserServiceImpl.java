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

import mblog.base.lang.EntityStatus;
import mblog.base.lang.MtonsException;
import mblog.base.utils.MD5;
import mblog.core.data.AccountProfile;
import mblog.core.data.AuthMenu;
import mblog.core.data.BadgesCount;
import mblog.core.data.User;
import mblog.core.persist.dao.RoleDao;
import mblog.core.persist.dao.UserDao;
import mblog.core.persist.entity.AuthMenuPO;
import mblog.core.persist.entity.RolePO;
import mblog.core.persist.entity.UserPO;
import mblog.core.persist.service.NotifyService;
import mblog.core.persist.service.UserService;
import mblog.core.persist.utils.BeanMapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "usersCaches")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private NotifyService notifyService;

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public AccountProfile login(String username, String password) {
		UserPO po = userDao.findByUsername(username);
		AccountProfile u = null;

		Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");

		Assert.state(StringUtils.equals(po.getPassword(), password), "密码错误");

		po.setLastLogin(Calendar.getInstance().getTime());
		userDao.save(po);
		u = BeanMapUtils.copyPassport(po);

		BadgesCount badgesCount = new BadgesCount();
		badgesCount.setNotifies(notifyService.unread4Me(u.getId()));

		u.setBadgesCount(badgesCount);
		return u;
	}

	@Override
	@Transactional
	public AccountProfile getProfileByName(String username) {
		UserPO po = userDao.findByUsername(username);
		AccountProfile u = null;

		Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");
		po.setLastLogin(Calendar.getInstance().getTime());

		u = BeanMapUtils.copyPassport(po);

		BadgesCount badgesCount = new BadgesCount();
		badgesCount.setNotifies(notifyService.unread4Me(u.getId()));

		u.setBadgesCount(badgesCount);

		return u;
	}

	@Override
	@Transactional
	public User register(User user) {
		Assert.notNull(user, "Parameter user can not be null!");

		Assert.hasLength(user.getUsername(), "用户名不能为空!");
//		Assert.hasLength(user.getEmail(), "邮箱不能为空!");
		Assert.hasLength(user.getPassword(), "密码不能为空!");

		UserPO check = userDao.findByUsername(user.getUsername());

		Assert.isNull(check, "用户名已经存在!");

		if (StringUtils.isNotBlank(user.getEmail())) {
			check = userDao.findByEmail(user.getEmail());
			Assert.isNull(check, "邮箱已经被注册!");
		}

		UserPO po = new UserPO();

		BeanUtils.copyProperties(user, po);

		Date now = Calendar.getInstance().getTime();
		po.setPassword(MD5.md5(user.getPassword()));
		po.setStatus(EntityStatus.ENABLED);
		po.setActiveEmail(EntityStatus.ENABLED);
		po.setCreated(now);

		userDao.save(po);

		return BeanMapUtils.copy(po, 0);
	}

	@Override
	@Transactional
	@CacheEvict(key = "#user.getId()")
	public AccountProfile update(User user) {
		UserPO po = userDao.findOne(user.getId());
		if (null != po) {
			po.setName(user.getName());
			po.setSignature(user.getSignature());
			userDao.save(po);
		}
		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Transactional
	@CacheEvict(key = "#id")
	public AccountProfile updateEmail(long id, String email) {
		UserPO po = userDao.findOne(id);

		if (null != po) {

			if (email.equals(po.getEmail())) {
				throw new MtonsException("邮箱地址没做更改");
			}

			UserPO check = userDao.findByEmail(email);

			if (check != null && check.getId() != po.getId()) {
				throw new MtonsException("该邮箱地址已经被使用了");
			}
			po.setEmail(email);
			po.setActiveEmail(EntityStatus.ENABLED);

			userDao.save(po);
		}

		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Cacheable(key = "#userId")
	public User get(long userId) {
		UserPO po = userDao.findOne(userId);
		User ret = null;
		if (po != null) {
			ret = BeanMapUtils.copy(po, 0);
		}
		return ret;
	}

	
	@Override
	public List<User> findHotUserByfans(){
		List<User> rets = new ArrayList<>();
		List<UserPO> list = userDao.findTop12ByOrderByFansDesc();
		for (UserPO po : list) {
			User u = BeanMapUtils.copy(po , 0);
			rets.add(u);
		}
		return rets;
	}
	
	@Override
	public User getByUsername(String username) {
		UserPO po = userDao.findByUsername(username);
		User ret = null;
		if (po != null) {
			ret = BeanMapUtils.copy(po, 0);
		}
		return ret;
	}

	@Override
	@Transactional
	@CacheEvict(key = "#id")
	public AccountProfile updateAvatar(long id, String path) {
		UserPO po = userDao.findOne(id);
		if (po != null) {
			po.setAvatar(path);
			userDao.save(po);
		}
		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Transactional
	public void updatePassword(long id, String newPassword) {
		UserPO po = userDao.findOne(id);

		Assert.hasLength(newPassword, "密码不能为空!");

		if (null != po) {
			po.setPassword(MD5.md5(newPassword));
			userDao.save(po);
		}
	}

	@Override
	@Transactional
	public void updatePassword(long id, String oldPassword, String newPassword) {
		UserPO po = userDao.findOne(id);

		Assert.hasLength(newPassword, "密码不能为空!");

		if (po != null) {
			Assert.isTrue(MD5.md5(oldPassword).equals(po.getPassword()), "当前密码不正确");
			po.setPassword(MD5.md5(newPassword));
			userDao.save(po);
		}
	}

	@Override
	@Transactional
	public void updateStatus(long id, int status) {
		UserPO po = userDao.findOne(id);

		if (po != null) {
			po.setStatus(status);
			userDao.save(po);
		}
	}

	@Override
	@Transactional
	public AccountProfile updateActiveEmail(long id, int activeEmail) {
		UserPO po = userDao.findOne(id);

		if (po != null) {
			po.setActiveEmail(activeEmail);
			userDao.save(po);
		}
		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Transactional
	public void updateRole(long id, Long[] roleIds) {
		List<RolePO> rolePOs = new ArrayList<>();
		for(Long roleId:roleIds){
			RolePO rolePO = roleDao.findOne(roleId);
			rolePOs.add(rolePO);
		}
		UserPO po = userDao.findOne(id);

		if (po != null) {
			po.setRoles(rolePOs);
		}
	}

	@Override
	public Page<User> paging(Pageable pageable) {
		Page<UserPO> page = userDao.findAllByOrderByIdDesc(pageable);
		List<User> rets = new ArrayList<>();

		for (UserPO po : page.getContent()) {
			User u = BeanMapUtils.copy(po , 1);
			rets.add(u);
		}

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Map<Long, User> findMapByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}
		List<UserPO> list = userDao.findAllByIdIn(ids);
		Map<Long, User> ret = new HashMap<>();

		list.forEach(po -> {
			ret.put(po.getId(), BeanMapUtils.copy(po, 0));
		});
		return ret;
	}

	@Override
	public List<AuthMenu> getMenuList(long id) {
		List<AuthMenu> menus = new ArrayList<>();
		UserPO userPO = userDao.findOne(id);
		List<RolePO> roles = userPO.getRoles();
		for(RolePO role : roles){
			List<AuthMenuPO> menuPOs = role.getAuthMenus();
			for(AuthMenuPO menuPO : menuPOs){
				AuthMenu menu = BeanMapUtils.copy(menuPO);
				if(!menus.contains(menu)){
					menus.add(menu);
				}
			}
		}
		return menus;
	}

}
