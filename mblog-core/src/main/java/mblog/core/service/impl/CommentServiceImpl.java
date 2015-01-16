/**
 * 
 */
package mblog.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mblog.core.persist.dao.CommentDao;
import mblog.core.persist.dao.UserDao;
import mblog.core.persist.entity.CommentPO;
import mblog.core.pojos.Comment;
import mblog.core.service.CommentService;
import mblog.core.service.PostService;
import mblog.core.utils.BeanMapUtils;
import mtons.modules.pojos.Page;
import mtons.modules.pojos.UserContextHolder;
import mtons.modules.pojos.UserProfile;

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
	private PostService postService;
	
	@Override
	@Transactional(readOnly = true)
	public void paging(Page page, long toId) {
		List<CommentPO> list = commentDao.paging(page, toId);
		List<Comment> rets = new ArrayList<Comment>();
		for (CommentPO po : list) {
			rets.add(BeanMapUtils.copy(po));
		}
		page.setResults(rets);
	}
	
	@Override
	@Transactional
	public long post(Comment comment) {
		CommentPO po = new CommentPO();
		
		UserProfile up = UserContextHolder.getUserProfile();
		Assert.notNull(up, "not login");
		
		po.setAuthor(userDao.get(up.getId()));
		po.setToId(comment.getToId());
		po.setContent(comment.getContent());
		po.setCreated(new Date());
		
		commentDao.save(po);
		
		// 自增评论数
		postService.identityComments(comment.getToId());
		return po.getId();
	}
	
}
