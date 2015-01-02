/**
 * 
 */
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.persist.entity.UserPO;
import mtons.modules.persist.Dao;
import mtons.modules.pojos.Page;

/**
 * @author langhsu
 *
 */
public interface UserDao extends Dao<UserPO> {
	UserPO get(String username);
	List<UserPO> paging(Page page);
}
