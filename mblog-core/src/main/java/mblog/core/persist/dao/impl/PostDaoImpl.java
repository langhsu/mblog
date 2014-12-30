/**
 * 
 */
package mblog.core.persist.dao.impl;

import java.util.Collection;
import java.util.List;

import mblog.core.persist.dao.PostDao;
import mblog.core.persist.entity.PostPO;
import mtons.modules.persist.impl.DaoImpl;
import mtons.modules.pojos.Page;

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
	public List<PostPO> paging(Page page) {
		PagingQuery<PostPO> q = pagingQuery(page);
		q.desc("created");
		return q.list();
	}

	@Override
	public List<PostPO> pagingByUserId(Page page, long userId) {
		PagingQuery<PostPO> q = pagingQuery(page);
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
	public List<PostPO> findByIds(Collection<Long> ids) {
		return find(Restrictions.in("id", ids));
	}

}
