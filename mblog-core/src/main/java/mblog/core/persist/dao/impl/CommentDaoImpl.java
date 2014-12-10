/**
 * 
 */
package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.persist.dao.CommentDao;
import mblog.core.persist.entity.CommentPO;
import mtons.commons.persist.hibernate.DaoImpl;
import mtons.commons.pojos.Paging;

import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 *
 */
public class CommentDaoImpl extends DaoImpl<CommentPO> implements CommentDao {
	private static final long serialVersionUID = 1023552695901348149L;

	public CommentDaoImpl() {
		super(CommentPO.class);
	}

	@Override
	public List<CommentPO> paging(Paging paging, long toId) {
		PagingQuery<CommentPO> q = pagingQuery(paging);
		q.add(Restrictions.eq("toId", toId));
		q.desc("created");
		return q.list();
	}
}
