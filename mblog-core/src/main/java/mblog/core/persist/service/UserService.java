/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.service;

import mblog.core.data.AccountProfile;
import mblog.core.data.AuthMenu;
import mblog.core.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author langhsu
 *
 */
public interface UserService {
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	AccountProfile login(String username, String password);

	/**
	 * 登录,用于记住登录时获取用户信息
	 * @param username
	 * @return
	 */
	AccountProfile getProfileByName(String username);

	/**
	 * 注册
	 * @param user
	 */
	User register(User user);

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	AccountProfile update(User user);

	/**
	 * 修改用户信息
	 * @param email
	 * @return
	 */
	AccountProfile updateEmail(long id, String email);

	/**
	 * 查询单个用户
	 * @param id
	 * @return
	 */
	User get(long id);

	User getByUsername(String username);

	/**
	 * 修改头像
	 * @param id
	 * @param path
	 * @return
	 */
	AccountProfile updateAvatar(long id, String path);

	/**
	 * 修改密码
	 * @param id
	 * @param newPassword
	 */
	void updatePassword(long id, String newPassword);

	/**
	 * 修改密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 */
	void updatePassword(long id, String oldPassword, String newPassword);

	/**
	 * 修改用户状态
	 * @param id
	 * @param status
	 */
	void updateStatus(long id, int status);

	AccountProfile updateActiveEmail(long id, int activeEmail);

	void updateRole(long id, Long[] roleIds);

	/**
	 * 分页查询
	 * @param pageable
	 */
	Page<User> paging(Pageable pageable);

	Map<Long, User> findMapByIds(Set<Long> ids);

	List<AuthMenu> getMenuList(long id);
	
	List<User> findHotUserByfans();
}
