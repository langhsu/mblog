/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.Role;
import com.mtons.mblog.modules.service.RoleService;
import com.mtons.mblog.modules.service.UserRoleService;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author langhsu
 *
 */
@Controller("adminUserController")
@RequestMapping("/admin/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping("/list")
	public String list(String name, ModelMap model) {
		Pageable pageable = wrapPageable();
		Page<UserVO> page = userService.paging(pageable, name);

		List<UserVO> users = page.getContent();
		List<Long> userIds = new ArrayList<>();

		users.forEach(item -> {
			userIds.add(item.getId());
		});

		Map<Long, List<Role>> map = userRoleService.findMapByUserIds(userIds);
		users.forEach(item -> {
			item.setRoles(map.get(item.getId()));
		});

		model.put("name", name);
		model.put("page", page);
		return "/admin/user/list";
	}

	@RequestMapping("/view")
	public String view(Long id, ModelMap model) {
		UserVO view = userService.get(id);
		view.setRoles(userRoleService.listRoles(view.getId()));
		model.put("view", view);
		model.put("roles", roleService.list());
		return "/admin/user/view";
	}

	@PostMapping("/update_role")
//	@RequiresPermissions("user:role")
	public String postAuthc(Long id, @RequestParam(value = "roleIds", required=false) Set<Long> roleIds, ModelMap model) {
		userRoleService.updateRole(id, roleIds);
		model.put("data", Result.success());
		return "redirect:/admin/user/list";
	}

	@RequestMapping(value = "/pwd", method = RequestMethod.GET)
//	@RequiresPermissions("user:pwd")
	public String pwsView(Long id, ModelMap model) {
		UserVO ret = userService.get(id);
		model.put("view", ret);
		return "/admin/user/pwd";
	}

	@RequestMapping(value = "/pwd", method = RequestMethod.POST)
//	@RequiresPermissions("user:pwd")
	public String pwd(Long id, String newPassword, ModelMap model) {
		UserVO ret = userService.get(id);
		model.put("view", ret);

		try {
			userService.updatePassword(id, newPassword);
			model.put("data", Result.successMessage("修改成功"));
		} catch (IllegalArgumentException e) {
			model.put("data", Result.failure(e.getMessage()));
		}
		return "/admin/user/pwd";
	}

	@RequestMapping("/open")
//	@RequiresPermissions("user:open")
	@ResponseBody
	public Result open(Long id) {
		userService.updateStatus(id, Consts.STATUS_NORMAL);
		return Result.success();
	}

	@RequestMapping("/close")
//	@RequiresPermissions("user:close")
	@ResponseBody
	public Result close(Long id) {
		userService.updateStatus(id, Consts.STATUS_CLOSED);
		return Result.success();
	}
}
