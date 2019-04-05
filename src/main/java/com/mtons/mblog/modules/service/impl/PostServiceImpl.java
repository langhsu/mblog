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

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.utils.*;
import com.mtons.mblog.modules.aspect.PostStatusFilter;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.*;
import com.mtons.mblog.modules.event.PostUpdateEvent;
import com.mtons.mblog.modules.repository.PicRepository;
import com.mtons.mblog.modules.repository.PostAttributeRepository;
import com.mtons.mblog.modules.repository.PostPicRepository;
import com.mtons.mblog.modules.repository.PostRepository;
import com.mtons.mblog.modules.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author langhsu
 *
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostAttributeRepository postAttributeRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private PostPicRepository postPicRepository;
	@Autowired
	private PicRepository picRepository;

	@Override
	@PostStatusFilter
	public Page<PostVO> paging(Pageable pageable, int channelId, Set<Integer> excludeChannelIds) {
		Page<Post> page = postRepository.findAll((root, query, builder) -> {
			Predicate predicate = builder.conjunction();

			if (channelId > Consts.ZERO) {
				predicate.getExpressions().add(
						builder.equal(root.get("channelId").as(Integer.class), channelId));
			}

			if (null != excludeChannelIds && !excludeChannelIds.isEmpty()) {
				predicate.getExpressions().add(
						builder.not(root.get("channelId").in(excludeChannelIds)));
			}

//			predicate.getExpressions().add(
//					builder.equal(root.get("featured").as(Integer.class), Consts.FEATURED_DEFAULT));

			return predicate;
		}, pageable);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public Page<PostVO> paging4Admin(Pageable pageable, int channelId, String title) {
		Page<Post> page = postRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();
			if (channelId > Consts.ZERO) {
				predicate.getExpressions().add(
						builder.equal(root.get("channelId").as(Integer.class), channelId));
			}
			if (StringUtils.isNotBlank(title)) {
				predicate.getExpressions().add(
						builder.like(root.get("title").as(String.class), "%" + title + "%"));
			}
            return predicate;
        }, pageable);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	@PostStatusFilter
	public Page<PostVO> pagingByAuthorId(Pageable pageable, long userId) {
		Page<Post> page = postRepository.findAllByAuthorId(pageable, userId);
		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	@PostStatusFilter
	public List<PostVO> findLatestPosts(int maxResults) {
		return find("created", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
	}
	
	@Override
	@PostStatusFilter
	public List<PostVO> findHottestPosts(int maxResults) {
		return find("views", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
	}
	
	@Override
	@PostStatusFilter
	public Map<Long, PostVO> findMapByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}

		List<Post> list = postRepository.findAllById(ids);
		Map<Long, PostVO> rets = new HashMap<>();

		HashSet<Long> uids = new HashSet<>();

		list.forEach(po -> {
			rets.put(po.getId(), BeanMapUtils.copy(po));
			uids.add(po.getAuthorId());
		});
		
		// 加载用户信息
		buildUsers(rets.values(), uids);
		return rets;
	}

	@Override
	@Transactional
	public long post(PostVO post) {
		Post po = new Post();

		BeanUtils.copyProperties(post, po);

		po.setCreated(new Date());
		po.setStatus(post.getStatus());

		// 处理摘要
		if (StringUtils.isBlank(post.getSummary())) {
			po.setSummary(trimSummary(post.getEditor(), post.getContent()));
		} else {
			po.setSummary(post.getSummary());
		}

		postRepository.save(po);
		tagService.batchUpdate(po.getTags(), po.getId());

		PostAttribute attr = new PostAttribute();
		attr.setContent(post.getContent());
		attr.setEditor(post.getEditor());
		attr.setId(po.getId());
		postAttributeRepository.save(attr);

		countPic(po.getId(), null,  attr.getContent());
		onPushEvent(po, PostUpdateEvent.ACTION_PUBLISH);
		return po.getId();
	}
	
	@Override
	public PostVO get(long id) {
		Optional<Post> po = postRepository.findById(id);
		if (po.isPresent()) {
			PostVO d = BeanMapUtils.copy(po.get());

			d.setAuthor(userService.get(d.getAuthorId()));
			d.setChannel(channelService.getById(d.getChannelId()));

			PostAttribute attr = postAttributeRepository.findById(d.getId()).get();
			d.setContent(attr.getContent());
			d.setEditor(attr.getEditor());
			return d;
		}
		return null;
	}

	/**
	 * 更新文章方法
	 * @param p
	 */
	@Override
	@Transactional
	public void update(PostVO p){
		Optional<Post> optional = postRepository.findById(p.getId());

		if (optional.isPresent()) {
			Post po = optional.get();
			po.setTitle(p.getTitle());//标题
			po.setChannelId(p.getChannelId());
			po.setThumbnail(p.getThumbnail());
			po.setStatus(p.getStatus());

			// 处理摘要
			if (StringUtils.isBlank(p.getSummary())) {
				po.setSummary(trimSummary(p.getEditor(), p.getContent()));
			} else {
				po.setSummary(p.getSummary());
			}

			po.setTags(p.getTags());//标签

			// 保存扩展
			Optional<PostAttribute> attributeOptional = postAttributeRepository.findById(po.getId());
			String originContent = "";
			if (attributeOptional.isPresent()){
				originContent = attributeOptional.get().getContent();
			}
			PostAttribute attr = new PostAttribute();
			attr.setContent(p.getContent());
			attr.setEditor(p.getEditor());
			attr.setId(po.getId());
			postAttributeRepository.save(attr);

			tagService.batchUpdate(po.getTags(), po.getId());

			countPic(po.getId(), originContent, p.getContent());
		}else{
			cleanPostPic(p.getId());
		}
	}

	@Override
	@Transactional
	public void updateFeatured(long id, int featured) {
		Post po = postRepository.findById(id).get();
		int status = Consts.FEATURED_ACTIVE == featured ? Consts.FEATURED_ACTIVE: Consts.FEATURED_DEFAULT;
		po.setFeatured(status);
		postRepository.save(po);
	}

	@Override
	@Transactional
	public void updateWeight(long id, int weighted) {
		Post po = postRepository.findById(id).get();

		int max = Consts.ZERO;
		if (Consts.FEATURED_ACTIVE == weighted) {
			max = postRepository.maxWeight() + 1;
		}
		po.setWeight(max);
		postRepository.save(po);
	}

	@Override
	@Transactional
	public void delete(long id, long authorId) {
		Post po = postRepository.findById(id).get();
		// 判断文章是否属于当前登录用户
		Assert.isTrue(po.getAuthorId() == authorId, "认证失败");

		postRepository.deleteById(id);
		postAttributeRepository.deleteById(id);

		onPushEvent(po, PostUpdateEvent.ACTION_DELETE);
	}

	@Override
	@Transactional
	public void delete(Collection<Long> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			List<Post> list = postRepository.findAllById(ids);
			list.forEach(po -> {
				postRepository.delete(po);
				postAttributeRepository.deleteById(po.getId());
				onPushEvent(po, PostUpdateEvent.ACTION_DELETE);
			});
		}
	}

	@Override
	@Transactional
	public void identityViews(long id) {
		// 次数不清理缓存, 等待文章缓存自动过期
		postRepository.updateViews(id, Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional
	public void identityComments(long id) {
		postRepository.updateComments(id, Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional
	public void favor(long userId, long postId) {
		postRepository.updateFavors(postId, Consts.IDENTITY_STEP);
		favoriteService.add(userId, postId);
	}

	@Override
	@Transactional
	public void unfavor(long userId, long postId) {
		postRepository.updateFavors(postId,  Consts.DECREASE_STEP);
		favoriteService.delete(userId, postId);
	}

	@Override
	@PostStatusFilter
	public long count() {
		return postRepository.count();
	}

	@PostStatusFilter
	private List<Post> find(String orderBy, int size) {
		Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, orderBy));

		Set<Integer> excludeChannelIds = new HashSet<>();

		List<Channel> channels = channelService.findAll(Consts.STATUS_CLOSED);
		if (channels != null) {
			channels.forEach((c) -> excludeChannelIds.add(c.getId()));
		}

		Page<Post> page = postRepository.findAll((root, query, builder) -> {
			Predicate predicate = builder.conjunction();
			if (excludeChannelIds.size() > 0) {
				predicate.getExpressions().add(
						builder.not(root.get("channelId").in(excludeChannelIds)));
			}
			return predicate;
		}, pageable);
		return page.getContent();
	}

	/**
	 * 截取文章内容
	 * @param text
	 * @return
	 */
	private String trimSummary(String editor, final String text){
		if (Consts.EDITOR_MARKDOWN.endsWith(editor)) {
			return PreviewTextUtils.getText(MarkdownUtils.renderMarkdown(text), 126);
		} else {
			return PreviewTextUtils.getText(text, 126);
		}
	}

	private List<PostVO> toPosts(List<Post> posts) {
		List<PostVO> rets = new ArrayList<>();

		HashSet<Long> uids = new HashSet<>();
		HashSet<Integer> groupIds = new HashSet<>();

		posts.forEach(po -> {
			uids.add(po.getAuthorId());
			groupIds.add(po.getChannelId());
			rets.add(BeanMapUtils.copy(po));
		});

		// 加载用户信息
		buildUsers(rets, uids);
		buildGroups(rets, groupIds);

		return rets;
	}

	private void buildUsers(Collection<PostVO> posts, Set<Long> uids) {
		Map<Long, UserVO> userMap = userService.findMapByIds(uids);
		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	private void buildGroups(Collection<PostVO> posts, Set<Integer> groupIds) {
		Map<Integer, Channel> map = channelService.findMapByIds(groupIds);
		posts.forEach(p -> p.setChannel(map.get(p.getChannelId())));
	}

	private void onPushEvent(Post post, int action) {
		PostUpdateEvent event = new PostUpdateEvent(System.currentTimeMillis());
		event.setPostId(post.getId());
		event.setUserId(post.getAuthorId());
		event.setAction(action);
		applicationContext.publishEvent(event);
	}

	public void countPic(Long postId, String originContent, String newContent){
	    if (StringUtils.isEmpty(originContent)){
	        originContent = "";
        }
        if (StringUtils.isEmpty(newContent)){
	        newContent = "";
        }
		String key = ResourceLock.getPostKey(postId);
        AtomicInteger lock = ResourceLock.getAtomicInteger(key);
        synchronized (lock){
            Pattern pattern = Pattern.compile(Consts.PIC_MARK + "*[0-9]{1,40}");
            Matcher originMatcher = pattern.matcher(originContent);
            Matcher newMatcher = pattern.matcher(newContent);
            Map<Long, Integer> originMap = new LinkedHashMap<>();
            Map<Long, Integer> newMap = new LinkedHashMap<>();
            while (originMatcher.find()){
                String idStr = originMatcher.group().replaceAll(Consts.PIC_MARK, "").replaceAll("/", "");
                if (StringUtils.isNumeric(idStr)){
                    Long id = Long.parseLong(idStr);
                    if (originMap.containsKey(id)) {
                        originMap.put(id, originMap.get(id) + 1);
                    } else {
                        originMap.put(id, 1);
                    }
                }
            }
            while (newMatcher.find()){
                String idStr = newMatcher.group().replaceAll(Consts.PIC_MARK, "").replaceAll("/", "");
                if (StringUtils.isNumeric(idStr)){
                    Long id = Long.parseLong(idStr);
                    if (newMap.containsKey(id)) {
                        newMap.put(id, newMap.get(id) + 1);
                    } else {
                        newMap.put(id, 1);
                    }
                }
            }

            Set<Long> originIds = originMap.keySet();
            Set<Long> newIds = newMap.keySet();
            Set<Long> toRemoveOriginIds = new HashSet<>(originIds);
            toRemoveOriginIds.removeAll(newIds);

            newIds.forEach(id -> {
                Integer oldNum = originMap.get(id);
                if (oldNum == null){
                    oldNum = 0;
                }
                Integer newNum = newMap.get(id);
                if (newNum == null){
                    newNum = 0;
                }
                modPicCount(id, newNum - oldNum);
            });
            toRemoveOriginIds.forEach(id -> {
                Integer oldNum = originMap.get(id);
                if (oldNum == null){
                    oldNum = 0;
                }
                modPicCount(id, 0 - oldNum);
            });

            postPicRepository.deleteByPostId(postId);
            for (int i = 0; i < newIds.size(); i++) {
                PostPic postPic = new PostPic();
                Long picId = (Long) CollectionUtils.get(newIds, i);
                postPic.setId(IdUtils.getId());
                postPic.setPicId(picId);
                postPic.setSort(i);
                postPic.setPostId(postId);
                postPicRepository.save(postPic);
            }
		}
	}

	public void modPicCount(Long id, Integer diff){
	    if (diff == 0){
	        return;
        }
        String key = ResourceLock.getPicKey(id);
        AtomicInteger lock = ResourceLock.getAtomicInteger(key);
        synchronized (lock){
            Optional<Pic> picOptional = picRepository.findById(id);
            if (picOptional.isPresent()){
                Pic pic = picOptional.get();
                pic.setCount(pic.getCount() + diff);
                picRepository.save(pic);
            }
        }
    }



	public void cleanPostPic(long postId) {
		postPicRepository.deleteByPostId(postId);
	}
}
