/**
 * 
 */
package mblog.core.persist.dao.impl;

import java.util.Collection;
import java.util.List;

import mblog.core.persist.dao.PostDao;
import mblog.core.persist.entity.PostPO;
import mtons.commons.persist.hibernate.DaoImpl;
import mtons.commons.pojos.Paging;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 *
 */
public class PostDaoImpl extends DaoImpl<PostPO> implements PostDao {
	private static final long serialVersionUID = -8144066308316359853L;
	
	public PostDaoImpl() {
		super(PostPO.class);
	}
	
	@Override
	public Session getSession() {
		return super.session();
	}

	@Override
	public List<PostPO> paging(Paging paging) {
		PagingQuery<PostPO> q = pagingQuery(paging);
		q.desc("created");
		return q.list();
	}

	@Override
	public List<PostPO> pagingByUserId(Paging paging, long userId) {
		PagingQuery<PostPO> q = pagingQuery(paging);
		if (userId > 0) {
			q.add(Restrictions.eq("author.id", userId));
		}
		q.desc("created");
		return q.list();
	}

	@Override
	public List<PostPO> recents(int maxResutls, long ignoreUserId) {
		TopQuery<PostPO> q = topQuery(maxResutls);
		//q.add(Restrictions.eq("type", Const.TYPE_TEXT));
		if (ignoreUserId > 0) {
			q.add(Restrictions.ne("author.id", ignoreUserId));
		}
		q.desc("created");
		return q.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PostPO> findByIds(Collection<Long> ids) {
		Criteria c = createCriteria();
		c.add(Restrictions.in("id", ids));
		return c.list();
	}

}
