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

import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.BadgesCount;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.service.*;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户主页
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/users")
public class UsersController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private MessageService messageService;

	/**
	 * 我发布的文章
	 * @param model
	 * @return
	 */
	@GetMapping(value="/{userId}")
	public String posts(@PathVariable(value = "userId") Long userId, ModelMap model, HttpServletRequest request) {
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		model.put("pageNo", pageNo);
		initUser(userId, model);
		return view(Views.USER_POSTS);
	}

	/**
	 * 我发表的评论
	 * @param model
	 * @return
	 */
	@GetMapping(value="/{userId}/comments")
	public String comments(@PathVariable(value = "userId") Long userId, ModelMap model, HttpServletRequest request) {
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		model.put("pageNo", pageNo);
		initUser(userId, model);
		return view(Views.USER_COMMENTS);
	}

	/**
	 * 我喜欢过的文章
	 * @param model
	 * @return
	 */
	@GetMapping(value="/{userId}/favorites")
	public String favors(@PathVariable(value = "userId") Long userId,ModelMap model, HttpServletRequest request) {
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		model.put("pageNo", pageNo);
		initUser(userId, model);
		return view(Views.USER_FAVORITES);
	}

	/**
	 * 我的通知
	 * @param model
	 * @return
	 */
	@GetMapping(value="/{userId}/messages")
	public String notifies(@PathVariable(value = "userId") Long userId, ModelMap model, HttpServletRequest request) {
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		model.put("pageNo", pageNo);

		// 标记已读
		AccountProfile profile = getProfile();

		if (null == profile || profile.getId() != userId) {
			throw new MtonsException("您还没权限访问该页面");
		}

		messageService.readed4Me(profile.getId());

		initUser(userId, model);
		return view(Views.USER_MESSAGES);
	}

	private void initUser(long userId, ModelMap model) {
		model.put("user", userService.get(userId));
		boolean owner = false;

		AccountProfile profile = getProfile();
		if (null != profile && profile.getId() == userId) {
			owner = true;
			putProfile(userService.findProfile(profile.getId()));
		}
		model.put("owner", owner);
	}

}
