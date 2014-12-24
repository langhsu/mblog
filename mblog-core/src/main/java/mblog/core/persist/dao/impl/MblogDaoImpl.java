/**
 * 
 */
package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.persist.dao.MblogDao;
import mblog.core.persist.entity.MblogPO;
import mtons.commons.persist.hibernate.DaoImpl;
import mtons.commons.pojos.Paging;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 *
 */
public class MblogDaoImpl extends DaoImpl<MblogPO> implements MblogDao {
	private static final long serialVersionUID = -8144066308316359853L;
	
	public MblogDaoImpl() {
		super(MblogPO.class);
	}
	
	@Override
	public Session getSession() {
		return super.session();
	}

	@Override
	public List<MblogPO> paging(Paging paging) {
		PagingQuery<MblogPO> q = pagingQuery(paging);
		q.desc("created");
		return q.list();
	}

	@Override
	public List<MblogPO> pagingByUserId(Paging paging, long userId) {
		PagingQuery<MblogPO> q = pagingQuery(paging);
		if (userId > 0) {
			q.add(Restrictions.eq("author.id", userId));
		}
		q.desc("created");
		return q.list();
	}

	@Override
	public List<MblogPO> recents(int maxResutls, long ignoreUserId) {
		TopQuery<MblogPO> q = topQuery(maxResutls);
		//q.add(Restrictions.eq("type", Const.TYPE_TEXT));
		if (ignoreUserId > 0) {
			q.add(Restrictions.ne("author.id", ignoreUserId));
		}
		q.desc("created");
		return q.list();
	}

}
