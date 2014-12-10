/**
 * 
 */
package mblog.core.persist.dao.impl;

import mblog.core.persist.dao.UserDao;
import mblog.core.persist.entity.UserPO;
import mtons.commons.persist.hibernate.DaoImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

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
		Criteria c = createCriteria(UserPO.class);
		c.add(Restrictions.eq("username", username));
		return (UserPO) c.uniqueResult();
	}

}
