/**
 * 
 */
package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.persist.dao.PostsDao;
import mblog.core.persist.entity.PostsPO;
import mtons.commons.persist.hibernate.DaoImpl;
import mtons.commons.pojos.Paging;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 *
 */
public class PostsDaoImpl extends DaoImpl<PostsPO> implements PostsDao {
	private static final long serialVersionUID = -8144066308316359853L;
	
	public PostsDaoImpl() {
		super(PostsPO.class);
	}
	
	@Override
	public Session getSession() {
		return super.session();
	}

	@Override
	public List<PostsPO> paging(Paging paging) {
		PagingQuery<PostsPO> q = pagingQuery(paging);
		q.desc("created");
		return q.list();
	}

	@Override
	public List<PostsPO> pagingByUserId(Paging paging, long userId) {
		PagingQuery<PostsPO> q = pagingQuery(paging);
		if (userId > 0) {
			q.add(Restrictions.eq("author.id", userId));
		}
		q.desc("created");
		return q.list();
	}

	@Override
	public List<PostsPO> recents(int maxResutls, long ignoreUserId) {
		TopQuery<PostsPO> q = topQuery(maxResutls);
		//q.add(Restrictions.eq("type", Const.TYPE_TEXT));
		if (ignoreUserId > 0) {
			q.add(Restrictions.ne("author.id", ignoreUserId));
		}
		q.desc("created");
		return q.list();
	}

}
