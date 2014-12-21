/**
 * 
 */
package mblog.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mblog.core.persist.dao.AttachDao;
import mblog.core.persist.dao.MblogDao;
import mblog.core.persist.dao.UserDao;
import mblog.core.persist.entity.MblogPO;
import mblog.core.pojos.Attach;
import mblog.core.pojos.Mblog;
import mblog.core.pojos.User;
import mblog.core.service.AttachService;
import mblog.core.service.MblogService;
import mtons.commons.lang.EntityStatus;
import mtons.commons.pojos.Paging;
import mtons.commons.pojos.UserContextHolder;
import mtons.commons.pojos.UserProfile;
import mtons.commons.utils.PreviewHtmlUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author langhsu
 *
 */
public class MblogServiceImpl implements MblogService {
	@Autowired
	private MblogDao mblogDao;
	@Autowired
	private AttachDao attachDao;
	@Autowired
	private AttachService attachService;
	@Autowired
	private UserDao userDao;
	
	private static String[] IGNORE = new String[]{"author", "snapshot"};
	private static String[] IGNORE_LIST = new String[]{"author", "snapshot", "content"};
	
	@Override
	@Transactional(readOnly = true)
	public void paging(Paging paging) {
		List<MblogPO> list = mblogDao.paging(paging);
		List<Mblog> rets = new ArrayList<Mblog>();
		for (MblogPO po : list) {
			rets.add(toVo(po, 0));
		}
		paging.setResults(rets);
	}
	
	@Override
	@Transactional(readOnly = true)
	public void pagingByUserId(Paging paging, long userId) {
		List<MblogPO> list = mblogDao.pagingByUserId(paging, userId);
		List<Mblog> rets = new ArrayList<Mblog>();
		for (MblogPO po : list) {
			rets.add(toVo(po ,0));
		}
		paging.setResults(rets);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Mblog> recents(int maxResutls, long ignoreUserId) {
		List<MblogPO> list = mblogDao.recents(maxResutls, ignoreUserId);
		List<Mblog> rets = new ArrayList<Mblog>();
		for (MblogPO po : list) {
			rets.add(toVo(po, 0));
		}
		return rets;
	}
	
	@Override
	@Transactional
	public void add(Mblog post) {
		MblogPO po = mblogDao.get(post.getId());
		if (po != null) {
			po.setUpdated(new Date());
			// po.setProject(projectDao.get(art.getProjectId()));
			po.setTitle(post.getTitle());
			po.setContent(post.getContent());
			po.setSummary(trimSummary(post.getContent()));
			po.setTags(post.getTags());
		} else {
			po = new MblogPO();
			UserProfile up = UserContextHolder.getUserProfile();
			
			po.setAuthor(userDao.get(up.getId()));
			po.setCreated(new Date());
			po.setStatus(EntityStatus.ENABLED);
			
			// content
			po.setType(post.getType());
			po.setTitle(post.getTitle());
			po.setContent(post.getContent());
			po.setSummary(trimSummary(post.getContent())); // summary handle
			po.setTags(post.getTags());
			
			mblogDao.save(po);
		}
		
		// album handle
		if (post.getAlbums() != null) {
			for (int i = 0; i < post.getAlbums().size(); i++) {
				Attach a = post.getAlbums().get(i);
				a.setToId(po.getId());
				long id = attachService.add(a);
				if (i == 0) {
					po.setSnapshot(attachDao.get(id));
				}
			}
		}
	}
	
	@Override
	@Transactional
	public Mblog get(long id) {
		MblogPO po = mblogDao.get(id);
		Mblog d = null;
		if (po != null) {
			d = toVo(po, 1);
		}
		List<Attach> albs = attachService.list(d.getId());
		d.setAlbums(albs);
		return d;
	}
	
	@Override
	@Transactional
	public void delete(long id) {
		UserProfile up = UserContextHolder.getUserProfile();
		
		Assert.notNull(up, "用户认证失败, 请重新登录!");
		
		MblogPO po = mblogDao.get(id);
		if (po != null) {
			Assert.isTrue(po.getAuthor().getId() == up.getId(), "认证失败");
			attachService.deleteByToId(id);
			mblogDao.delete(po);
		}
	}
	
	private Mblog toVo(MblogPO po, int level) {
		Mblog d = new Mblog();
		if (level > 0) {
			BeanUtils.copyProperties(po, d, IGNORE);
		} else {
			BeanUtils.copyProperties(po, d, IGNORE_LIST);
		}
		
		if (po.getAuthor() != null) {
			User u = new User();
			u.setId(po.getAuthor().getId());
			u.setUsername(po.getAuthor().getUsername());
			u.setName(po.getAuthor().getName());
			u.setAvater(po.getAuthor().getAvater());
			d.setAuthor(u);
		}
		if (po.getSnapshot() != null) {
			Attach a = new Attach();
			BeanUtils.copyProperties(po.getSnapshot(), a);
			d.setSnapshot(a);
		}
		return d;
	}
	
	/**
     * 截取文章内容
     * @param text
     * @return
     */
    private String trimSummary(String text){
        return PreviewHtmlUtils.truncateHTML(text, 126);
    }

}
