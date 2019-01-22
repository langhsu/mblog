/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.site.user;

import com.mtons.mblog.modules.data.*;
import com.mtons.mblog.modules.service.*;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户主页
 * @author langhsu
 *
 */
@Controller
public class UserController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private FavorService favorService;
	@Autowired
	private MessageService messageService;

	/**
	 * 我发布的文章
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user")
	public String posts(ModelMap model) {
		Pageable pageable = wrapPageable();
		Page<PostVO> page = postService.pagingByAuthorId(pageable, getProfile().getId());

		model.put("page", page);
		initUser(model);

		return view(Views.USER_POSTS);
	}

	/**
	 * 我发表的评论
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user", params = "method=comments")
	public String comments(ModelMap model) {
		Pageable pageable = wrapPageable();
		AccountProfile profile = getProfile();
		Page<CommentVO> page = commentService.paging4Home(pageable, profile.getId());

		model.put("page", page);
		initUser(model);

		return view(Views.USER_COMMENTS);
	}

	/**
	 * 我喜欢过的文章
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user", params = "method=favors")
	public String favors(ModelMap model) {
		Pageable pageable = wrapPageable();
		AccountProfile profile = getProfile();
		Page<FavorVO> page = favorService.pagingByOwnId(pageable, profile.getId());

		model.put("page", page);
		initUser(model);

		return view(Views.USER_FAVORS);
	}

	/**
	 * 我的通知
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user", params = "method=messages")
	public String notifies(ModelMap model) {
		Pageable pageable = wrapPageable();
		AccountProfile profile = getProfile();
		Page<MessageVO> page = messageService.findByOwnId(pageable, profile.getId());
		// 标记已读
		messageService.readed4Me(profile.getId());

		model.put("page", page);
		initUser(model);

		return view(Views.USER_MESSAGES);
	}

	private void initUser(ModelMap model) {
		AccountProfile up = getProfile();
		UserVO user = userService.get(up.getId());

		model.put("user", user);

		pushBadgesCount();
	}

	private void pushBadgesCount() {
		AccountProfile profile = (AccountProfile) session.getAttribute("profile");
		if (profile != null && profile.getBadgesCount() != null) {
			BadgesCount count = new BadgesCount();
			count.setMessages(messageService.unread4Me(profile.getId()));
			profile.setBadgesCount(count);
			session.setAttribute("profile", profile);
		}
	}

}
