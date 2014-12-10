/**
 * 
 */
package hilo.core.persist.dao.impl;

import hilo.core.persist.dao.CommentDao;
import hilo.core.persist.entity.CommentPO;

import java.util.List;

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
	public List<CommentPO> paging(Paging paging, int projectId, long contentId) {
		PagingQuery<CommentPO> q = pagingQuery(paging);
		if (projectId > 0) {
			q.add(Restrictions.eq("project.id", projectId));
		}
		q.add(Restrictions.eq("toId", contentId));
		q.desc("created");
		return q.list();
	}
}
