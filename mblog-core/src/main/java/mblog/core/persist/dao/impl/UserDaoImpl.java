/**
 * 
 */
package mblog.core.persist.dao.impl;

import mblog.core.persist.dao.UserDao;
import mblog.core.persist.entity.UserPO;
import mtons.modules.persist.impl.DaoImpl;

/**
 * @author langhsu
 *
 */
public class UserDaoImpl extends DaoImpl<UserPO> implements UserDao {
	private static final long serialVersionUID = -3396151113305189145L;

	public UserDaoImpl() {
		super(UserPO.class);
	}
	
	@Override
	public UserPO get(String username) {
		return (UserPO) findUniqueBy("username", username);
	}

}
