/**
 * 
 */
package hilo.core.persist.dao.impl;

import hilo.core.persist.dao.ProjectDao;
import hilo.core.persist.entity.ProjectPO;

import java.util.List;

import mtons.commons.persist.hibernate.DaoImpl;

import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 *
 */
public class ProjectDaoImpl extends DaoImpl<ProjectPO> implements ProjectDao {
	private static final long serialVersionUID = -7136713940144326525L;
	
	public ProjectDaoImpl() {
		super(ProjectPO.class);
	}
	
	@Override
	public List<ProjectPO> find(int level) {
		TopQuery<ProjectPO> c = topQuery(0);
		c.add(Restrictions.eq("level", level));
		return c.list();
	}

}
