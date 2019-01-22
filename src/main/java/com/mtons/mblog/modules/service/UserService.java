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

import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
	UserVO register(UserVO user);

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	AccountProfile update(UserVO user);

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
	UserVO get(long id);

	UserVO getByUsername(String username);

	UserVO getByEmail(String email);

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

	/**
	 * 分页查询
	 * @param pageable
	 */
	Page<UserVO> paging(Pageable pageable);

	Map<Long, UserVO> findMapByIds(Set<Long> ids);
}
