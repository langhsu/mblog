/**
 * 
 */
package mblog.core.service;

import mblog.core.pojos.User;
import mtons.modules.pojos.Page;
import mtons.modules.pojos.UserProfile;

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
	UserProfile login(String username, String password);
	
	/**
	 * 注册
	 * @param user
	 */
	void register(User user);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	UserProfile update(User user);
	
	/**
	 * 查询单个用户
	 * @param id
	 * @return
	 */
	User get(long id);
	
	/**
	 * 修改头像
	 * @param id
	 * @param path
	 * @return
	 */
	UserProfile updateAvatar(long id, String path);
	
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
	 * @param page
	 */
	void paging(Page page);
}
