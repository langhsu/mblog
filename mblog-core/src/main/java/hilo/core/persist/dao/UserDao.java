/**
 * 
 */
package hilo.core.persist.dao;

import hilo.core.persist.entity.UserPO;
import mtons.commons.persist.Dao;

/**
 * @author langhsu
 *
 */
public interface UserDao extends Dao<UserPO> {
	UserPO get(String username);
}
