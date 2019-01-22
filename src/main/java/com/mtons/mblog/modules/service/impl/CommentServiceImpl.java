/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.data.CommentVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.Comment;
import com.mtons.mblog.modules.repository.CommentRepository;
import com.mtons.mblog.modules.service.CommentService;
import com.mtons.mblog.modules.service.UserEventService;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.modules.utils.BeanMapUtils;
import com.mtons.mblog.modules.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author langhsu
 *
 */
@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserEventService userEventService;
	@Autowired
	private PostService postService;
	
	@Override
	public Page<CommentVO> paging4Admin(Pageable pageable) {
		Page<Comment> page = commentRepository.findAll(pageable);
		List<CommentVO> rets = new ArrayList<>();

		HashSet<Long> uids= new HashSet<>();

		page.getContent().forEach(po -> {
			uids.add(po.getAuthorId());
			rets.add(BeanMapUtils.copy(po));
		});

		buildUsers(rets, uids);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Page<CommentVO> paging4Home(Pageable pageable, long authorId) {
		Page<Comment> page = commentRepository.findAllByAuthorIdOrderByCreatedDesc(pageable, authorId);

		List<CommentVO> rets = new ArrayList<>();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> uids = new HashSet<>();
		Set<Long> postIds = new HashSet<>();

		page.getContent().forEach(po -> {
			CommentVO c = BeanMapUtils.copy(po);

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());
			postIds.add(c.getToId());

			rets.add(c);
		});

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, uids);
		buildPosts(rets, postIds);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Page<CommentVO> paging(Pageable pageable, long toId) {
		Page<Comment> page = commentRepository.findAllByToIdOrderByCreatedDesc(pageable, toId);
		
		List<CommentVO> rets = new ArrayList<>();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> uids = new HashSet<>();

		page.getContent().forEach(po -> {
			CommentVO c = BeanMapUtils.copy(po);

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());

			rets.add(c);
		});

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, uids);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Map<Long, CommentVO> findByIds(Set<Long> ids) {
		List<Comment> list = commentRepository.findByIdIn(ids);
		Map<Long, CommentVO> ret = new HashMap<>();
		Set<Long> uids = new HashSet<>();

		list.forEach(po -> {
			uids.add(po.getAuthorId());
			ret.put(po.getId(), BeanMapUtils.copy(po));
		});

		buildUsers(ret.values(), uids);
		return ret;
	}

	@Override
	@Transactional
	public long post(CommentVO comment) {
		Comment po = new Comment();
		
		po.setAuthorId(comment.getAuthorId());
		po.setToId(comment.getToId());
		po.setContent(comment.getContent());
		po.setCreated(new Date());
		po.setPid(comment.getPid());
		commentRepository.save(po);

		userEventService.identityComment(comment.getAuthorId(), po.getId(), true);
		return po.getId();
	}

	@Override
	@Transactional
	public void delete(List<Long> ids) {
		commentRepository.deleteAllByIdIn(ids);
	}

	@Override
	@Transactional
	public void delete(long id, long authorId) {
		Comment po = commentRepository.findOne(id);
		if (po != null) {
			// 判断文章是否属于当前登录用户
			Assert.isTrue(po.getAuthorId() == authorId, "认证失败");
			commentRepository.delete(po);
		}
	}

	@Override
	@Transactional
	public List<Comment> findAllByAuthorIdAndToId(long authorId, long toId) {
		return commentRepository.findAllByAuthorIdAndToIdOrderByCreatedDesc(authorId, toId);
	}

	@Override
	public List<CommentVO> latests(int maxResults) {
		Pageable pageable = new PageRequest(0, maxResults, new Sort(Sort.Direction.DESC, "id"));
		Page<Comment> page = commentRepository.findAll(pageable);
		List<CommentVO> rets = new ArrayList<>();

		HashSet<Long> uids= new HashSet<>();

		page.getContent().forEach(po -> {
			uids.add(po.getAuthorId());
			rets.add(BeanMapUtils.copy(po));
		});

		buildUsers(rets, uids);
		return rets;
	}

	private void buildUsers(Collection<CommentVO> posts, Set<Long> uids) {
		Map<Long, UserVO> userMap = userService.findMapByIds(uids);

		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	private void buildPosts(Collection<CommentVO> comments, Set<Long> postIds) {
		Map<Long, PostVO> postMap = postService.findMapByIds(postIds);

		comments.forEach(p -> p.setPost(postMap.get(p.getToId())));
	}

	private void buildParent(Collection<CommentVO> comments, Set<Long> parentIds) {
		if (!parentIds.isEmpty()) {
			Map<Long, CommentVO> pm = findByIds(parentIds);

			comments.forEach(c -> {
				if (c.getPid() > 0) {
					c.setParent(pm.get(c.getPid()));
				}
			});
		}
	}

}
