/**
 * 
 */
package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.persist.dao.UserDao;
import mblog.core.persist.entity.UserPO;
import mtons.modules.persist.impl.DaoImpl;
import mtons.modules.pojos.Page;

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
		return findUniqueBy("username", username);
	}

	@Override
	public List<UserPO> paging(Page page) {
		PagingQuery<UserPO> q = pagingQuery(page);
		q.desc("id");
		return q.list();
	}

}
