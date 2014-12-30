/**
 * 
 */
package mblog.core.persist.dao;

import mblog.core.persist.entity.UserPO;
import mtons.modules.persist.Dao;

/**
 * @author langhsu
 *
 */
public interface UserDao extends Dao<UserPO> {
	UserPO get(String username);
}
