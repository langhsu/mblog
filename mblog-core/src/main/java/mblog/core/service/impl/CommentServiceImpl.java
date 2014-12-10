/**
 * 
 */
package mblog.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mblog.core.persist.dao.CommentDao;
import mblog.core.persist.dao.ProjectDao;
import mblog.core.persist.dao.UserDao;
import mblog.core.persist.entity.CommentPO;
import mblog.core.pojos.Comment;
import mblog.core.pojos.User;
import mblog.core.service.CommentService;
import mtons.commons.pojos.Paging;
import mtons.commons.pojos.UserContextHolder;
import mtons.commons.pojos.UserProfile;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author langhsu
 *
 */
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProjectDao projectDao;
	
	private static String[] IGNORE = new String[]{"owner", "project"};
	
	@Override
	@Transactional(readOnly = true)
	public void paging(Paging paging, int projectId, long contentId) {
		List<CommentPO> list = commentDao.paging(paging, projectId, contentId);
		List<Comment> rets = new ArrayList<Comment>();
		for (CommentPO po : list) {
			rets.add(toVo(po));
		}
		paging.setResults(rets);
	}
	
	@Override
	@Transactional
	public long post(Comment comment) {
		CommentPO po = new CommentPO();
		
		UserProfile up = UserContextHolder.getUserProfile();
		Assert.notNull(up, "not login");
		
		po.setOwner(userDao.get(up.getId()));
		po.setProject(projectDao.get(comment.getProjectId()));
		po.setToId(comment.getToId());
		po.setContent(comment.getContent());
		po.setCreated(new Date());
		
		commentDao.save(po);
		return po.getId();
	}
	
	private Comment toVo(CommentPO po) {
		Comment d = new Comment();
		BeanUtils.copyProperties(po, d, IGNORE);
		
		if (po.getOwner() != null) {
			User u = new User();
			u.setId(po.getOwner().getId());
			u.setUsername(po.getOwner().getUsername());
			u.setNickname(po.getOwner().getNickname());
			d.setOwner(u);
		}
		return d;
	}

}
