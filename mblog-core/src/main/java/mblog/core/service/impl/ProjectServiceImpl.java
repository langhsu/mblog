/**
 * 
 */
package mblog.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import mblog.core.persist.dao.ProjectDao;
import mblog.core.persist.entity.ProjectPO;
import mblog.core.pojos.Project;
import mblog.core.service.ProjectService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author langhsu
 *
 */
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Project> list(int level) {
		List<ProjectPO> list = projectDao.find(level);
		List<Project> rets = new ArrayList<Project>();
		for (ProjectPO po : list) {
			Project p = new Project();
			BeanUtils.copyProperties(po, p);
			rets.add(p);
		}
		return rets;
	}

}
