/**
 * 
 */
package hilo.core.service;

import hilo.core.pojos.User;
import mtons.commons.pojos.UserProfile;

/**
 * @author langhsu
 *
 */
public interface UserService {
	UserProfile login(String username, String password);
	void register(User user);
	UserProfile update(User user);
	void updatePassword(long id, String newPassword);
}
