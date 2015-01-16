/**
 * 
 */
package mblog.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import mblog.core.persist.dao.AttachDao;
import mblog.core.persist.entity.AttachPO;
import mblog.core.pojos.Attach;
import mblog.core.service.AttachService;
import mblog.core.utils.BeanMapUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author langhsu
 *
 */
public class AttachServiceImpl implements AttachService {
	@Autowired
	private AttachDao attachDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Attach> list(long toId) {
		List<AttachPO> list = attachDao.list(toId);
		List<Attach> rets = new ArrayList<Attach>();
		for (AttachPO po : list) {
			rets.add(BeanMapUtils.copy(po));
		}
		return rets;
	}
	
	@Override
	@Transactional
	public long add(Attach album) {
		AttachPO po = new AttachPO();
		BeanUtils.copyProperties(album, po);
		attachDao.save(po);
		return po.getId();
	}
	
	@Override
	@Transactional
	public void deleteByToId(long toId) {
		List<AttachPO> list = attachDao.list(toId);
		
		for (AttachPO po : list) {
			//TODO: remove file
			attachDao.delete(po);
		}
	}

}
