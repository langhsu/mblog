/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.api;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.modules.data.CommentVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.service.CommentService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 侧边栏数据加载
 * 
 * @author langhsu
 *
 */
@RestController
@RequestMapping("/api")
public class SidebarController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/login")
	public Result login(String username, String password) {
		Result data = Result.failure("操作失败");

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return data;
		}

		AuthenticationToken token = createToken(username, password);
		if (token == null) {
			data.setMessage("用户名或密码错误");
			return data;
		}

		try {
			SecurityUtils.getSubject().login(token);
			data = Result.success("登录成功", getProfile());

		} catch (Exception e) {
			if (e instanceof UnknownAccountException) {
				data.setMessage("用户不存在");
			} else if (e instanceof LockedAccountException) {
				data.setMessage("用户被禁用");
			} else {
				data.setMessage("登录认证失败");
			}
		}
		return data;
	}

	@RequestMapping("/latests")
	public List<PostVO> latests(@RequestParam(name ="size", defaultValue = "6") Integer size) {
		return postService.findLatests(size);
	}
	
	@RequestMapping("/hots")
	public List<PostVO> hots(@RequestParam(name ="size", defaultValue = "6") Integer size) {
		return postService.findHottests(size);
	}
	
	@RequestMapping(value="/latest_comments")
	public @ResponseBody List<CommentVO> latestComments(@RequestParam(name ="size", defaultValue = "6") Integer size) {
         return commentService.findLatests(size);
	}
}
