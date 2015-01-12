/**
 * 
 */
package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.persist.dao.TagDao;
import mblog.core.persist.entity.TagPO;
import mtons.modules.persist.impl.DaoImpl;

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
		return findUniqueBy("name", name);
	}

	@Override
	public List<TagPO> tops(int maxResutls) {
		TopQuery<TagPO> q = topQuery(maxResutls);
		q.desc("featured");
		q.desc("hots");
		return q.list();
	}
}
