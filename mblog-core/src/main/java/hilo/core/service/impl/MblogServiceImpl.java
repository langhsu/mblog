/**
 * 
 */
package hilo.core.service.impl;

import hilo.core.persist.dao.AlbumDao;
import hilo.core.persist.dao.MblogDao;
import hilo.core.persist.dao.ProjectDao;
import hilo.core.persist.dao.UserDao;
import hilo.core.persist.entity.MblogPO;
import hilo.core.pojos.Album;
import hilo.core.pojos.Project;
import hilo.core.pojos.User;
import hilo.core.pojos.Mblog;
import hilo.core.service.AlbumService;
import hilo.core.service.MblogService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mtons.commons.lang.EntityStatus;
import mtons.commons.pojos.Paging;
import mtons.commons.pojos.UserContextHolder;
import mtons.commons.pojos.UserProfile;
import mtons.commons.utils.HtmlCutUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author langhsu
 *
 */
public class MblogServiceImpl implements MblogService {
	@Autowired
	private MblogDao mblogDao;
	@Autowired
	private AlbumDao albumDao;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProjectDao projectDao;
	
	private static String[] IGNORE = new String[]{"owner", "project", "snapshot"};
	private static String[] IGNORE_LIST = new String[]{"owner", "project", "snapshot", "content"};
	
	@Override
	@Transactional(readOnly = true)
	public void paging(Paging paging, int projectId) {
		List<MblogPO> list = mblogDao.paging(paging, projectId);
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
	public void add(Mblog weblog) {
		MblogPO po = mblogDao.get(weblog.getId());
		if (po != null) {
			po.setUpdated(new Date());
			// po.setProject(projectDao.get(art.getProjectId()));
			po.setTitle(weblog.getTitle());
			po.setContent(weblog.getContent());
			po.setSummary(trimSummary(weblog.getContent()));
			po.setTags(weblog.getTags());
		} else {
			po = new MblogPO();
			UserProfile up = UserContextHolder.getUserProfile();
			
			po.setOwner(userDao.get(up.getId()));
			po.setProject(projectDao.get(weblog.getProjectId()));
			po.setCreated(new Date());
			po.setStatus(EntityStatus.ENABLED);
			
			// content
			po.setType(weblog.getType());
			po.setTitle(weblog.getTitle());
			po.setContent(weblog.getContent());
			po.setSummary(trimSummary(weblog.getContent())); // summary handle
			po.setTags(weblog.getTags());
			
			mblogDao.save(po);
		}
		
		// album handle
		if (weblog.getAlbums() != null) {
			for (int i = 0; i < weblog.getAlbums().size(); i++) {
				Album a = weblog.getAlbums().get(i);
				a.setProjectId(weblog.getProjectId());
				a.setToId(po.getId());
				long id = albumService.add(a);
				if (i == 0) {
					po.setSnapshot(albumDao.get(id));
				}
			}
		}
	}
	
	@Override
	@Transactional
	public Mblog get(long artId) {
		MblogPO po = mblogDao.get(artId);
		Mblog d = null;
		if (po != null) {
			d = toVo(po, 1);
		}
		List<Album> albs = albumService.list(d.getProjectId(), d.getId());
		d.setAlbums(albs);
		return d;
	}
	
	private Mblog toVo(MblogPO po, int level) {
		Mblog d = new Mblog();
		//TODO The list of query optimization
		if (level > 0) {
			BeanUtils.copyProperties(po, d, IGNORE);
		} else {
			BeanUtils.copyProperties(po, d, IGNORE_LIST);
		}
		
		if (po.getOwner() != null) {
			User u = new User();
			u.setId(po.getOwner().getId());
			u.setUsername(po.getOwner().getUsername());
			u.setNickname(po.getOwner().getNickname());
			d.setOwner(u);
		}
		if (po.getProject() != null) {
			Project c = new Project();
			c.setId(po.getProject().getId());
			c.setName(po.getProject().getName());
			d.setProject(c);
			d.setProjectId(po.getProject().getId());
		}
		if (po.getSnapshot() != null) {
			Album a = new Album();
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
        return HtmlCutUtils.substring(text, 200);
    }

}
