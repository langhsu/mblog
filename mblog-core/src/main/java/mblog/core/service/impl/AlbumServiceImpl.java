/**
 * 
 */
package mblog.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import mblog.core.persist.dao.AlbumDao;
import mblog.core.persist.entity.AlbumPO;
import mblog.core.pojos.Album;
import mblog.core.service.AlbumService;

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
	
	private static String[] IGNORE = new String[]{};
	
	@Override
	@Transactional(readOnly = true)
	public List<Album> list(long toId) {
		List<AlbumPO> list = albumDao.list(toId);
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
		albumDao.save(po);
		return po.getId();
	}

	private Album toVo(AlbumPO po) {
		Album a = new Album();
		BeanUtils.copyProperties(po, a, IGNORE);
		return a;
	}
}
