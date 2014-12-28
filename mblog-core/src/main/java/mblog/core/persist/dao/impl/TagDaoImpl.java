/**
 * 
 */
package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.persist.dao.TagDao;
import mblog.core.persist.entity.TagPO;
import mtons.commons.persist.hibernate.DaoImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 * 
 */
public class TagDaoImpl extends DaoImpl<TagPO> implements TagDao {
	private static final long serialVersionUID = 3787316111507159374L;

	public TagDaoImpl() {
		super(TagPO.class);
	}

	@Override
	public TagPO getByName(String name) {
		Criteria c = createCriteria(TagPO.class);
		c.add(Restrictions.eq("name", name));
		return (TagPO) c.uniqueResult();
	}

	@Override
	public List<TagPO> tops(int maxResutls) {
		TopQuery<TagPO> q = topQuery(maxResutls);
		q.desc("featured");
		q.desc("hots");
		return q.list();
	}
}
