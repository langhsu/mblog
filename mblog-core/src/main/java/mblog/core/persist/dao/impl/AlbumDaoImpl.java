/**
 * 
 */
package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.persist.dao.AlbumDao;
import mblog.core.persist.entity.AlbumPO;
import mtons.commons.persist.hibernate.DaoImpl;

import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 *
 */
public class AlbumDaoImpl extends DaoImpl<AlbumPO> implements AlbumDao {
	private static final long serialVersionUID = -3561107849267517664L;

	public AlbumDaoImpl() {
		super(AlbumPO.class);
	}

	@Override
	public List<AlbumPO> list(int projectId, long toId) {
		TopQuery<AlbumPO> q = topQuery(0);
		if (projectId > 0) {
			q.add(Restrictions.eq("project.id", projectId));
		}
		q.add(Restrictions.eq("toId", toId));
		return q.list();
	}
}
