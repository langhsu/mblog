/**
 * 
 */
package mblog.core.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mblog.core.lang.Consts;
import mblog.core.persist.dao.TagDao;
import mblog.core.persist.entity.TagPO;
import mblog.core.pojos.Post;
import mblog.core.pojos.Tag;
import mblog.core.service.PostService;
import mblog.core.service.TagService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author langhsu
 *
 */
public class TagServiceImpl implements TagService {
	@Autowired
	private TagDao tagDao;
	@Autowired
	private PostService postService;
	
	@Override
	@Transactional(readOnly = true)
	public List<Tag> topTags(int maxResutls, boolean loadPost) {
		List<TagPO> list = tagDao.tops(maxResutls);
		List<Tag> rets = new ArrayList<Tag>();
		
		Set<Long> postIds = new HashSet<Long>();
		for (TagPO po : list) {
			Tag t = new Tag();
			BeanUtils.copyProperties(po, t);
			rets.add(t);
			postIds.add(t.getLastPostId());
		}
		
		if (loadPost && postIds.size() > 0) {
			Map<Long, Post> posts = postService.findByIds(postIds);
			
			for (Tag t : rets) {
				Post p = posts.get(t.getLastPostId());
				t.setPost(p);
			}
		}
		return rets;
	}
	
	@Override
	@Transactional
	public void batchPost(List<Tag> tags) {
		if (tags == null || tags.size() == 0) {
			return;
		}
		
		for (Tag t : tags) {
			if (StringUtils.isBlank(t.getName())) {
				continue;
			}
			TagPO po = tagDao.getByName(t.getName());
			if (po != null) {
				po.setLastPostId(t.getLastPostId());
				po.setPosts(po.getPosts() + 1);
			} else {
				po = new TagPO();
				BeanUtils.copyProperties(t, po);
				tagDao.save(po);
			}
		}
	}

	@Override
	@Transactional
	public void identityHots(String name) {
		TagPO po = tagDao.getByName(name);
		if (po != null) {
			po.setHots(po.getHots() + Consts.IDENTITY_STEP);
		}
	}

	@Override
	@Transactional
	public void identityHots(long id) {
		TagPO po = tagDao.get(id);
		if (po != null) {
			po.setHots(po.getHots() + Consts.IDENTITY_STEP);
		}
	}

}
