/**
 * 
 */
package hilo.core.service.impl;

import hilo.core.persist.dao.AlbumDao;
import hilo.core.persist.dao.ProjectDao;
import hilo.core.persist.entity.AlbumPO;
import hilo.core.pojos.Album;
import hilo.core.service.AlbumService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author langhsu
 *
 */
public class AlbumServiceImpl implements AlbumService {
	@Autowired
	private AlbumDao albumDao;
	@Autowired
	private ProjectDao projectDao;
	
	private static String[] IGNORE = new String[]{"project"};
	
	@Override
	@Transactional(readOnly = true)
	public List<Album> list(int projectId, long toId) {
		List<AlbumPO> list = albumDao.list(projectId, toId);
		List<Album> rets = new ArrayList<Album>();
		for (AlbumPO po : list) {
			rets.add(toVo(po));
		}
		return rets;
	}
	
	@Override
	@Transactional
	public long add(Album album) {
		AlbumPO po = new AlbumPO();
		BeanUtils.copyProperties(album, po, IGNORE);
		po.setProject(projectDao.get(album.getProjectId()));
		albumDao.save(po);
		return po.getId();
	}

	private Album toVo(AlbumPO po) {
		Album a = new Album();
		BeanUtils.copyProperties(po, a, IGNORE);
		return a;
	}
}
