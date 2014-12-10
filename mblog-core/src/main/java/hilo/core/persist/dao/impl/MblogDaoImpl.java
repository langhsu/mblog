/**
 * 
 */
package hilo.core.persist.dao.impl;

import hilo.core.persist.dao.MblogDao;
import hilo.core.persist.entity.MblogPO;

import java.util.List;

import mtons.commons.persist.hibernate.DaoImpl;
import mtons.commons.pojos.Paging;

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
	public List<MblogPO> paging(Paging paging, int projectId) {
		PagingQuery<MblogPO> q = pagingQuery(paging);
		if (projectId > 0) {
			q.add(Restrictions.eq("project.id", projectId));
		}
		q.desc("created");
		return q.list();
	}

	@Override
	public List<MblogPO> pagingByUserId(Paging paging, long userId) {
		PagingQuery<MblogPO> q = pagingQuery(paging);
		if (userId > 0) {
			q.add(Restrictions.eq("owner.id", userId));
		}
		q.desc("created");
		return q.list();
	}

	@Override
	public List<MblogPO> recents(int maxResutls, long ignoreUserId) {
		TopQuery<MblogPO> q = topQuery(maxResutls);
		//q.add(Restrictions.eq("type", Const.TYPE_TEXT));
		if (ignoreUserId > 0) {
			q.add(Restrictions.ne("owner.id", ignoreUserId));
		}
		q.desc("created");
		return q.list();
	}
	
}
