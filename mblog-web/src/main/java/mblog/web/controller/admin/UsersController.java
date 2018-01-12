/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.web.controller.admin;

import mblog.base.data.Data;
import mblog.base.lang.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mblog.core.data.User;
import mblog.core.persist.service.RoleService;
import mblog.core.persist.service.UserService;
import mblog.web.controller.BaseController;

/**
 * @author langhsu
 *
 */
@Controller("mng_user_ctl")
@RequestMapping("/admin/users")
public class UsersController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@RequestMapping("/list")
	public String list(ModelMap model) {
		Pageable pageable = wrapPageable();
		Page<User> page = userService.paging(pageable);
		model.put("page", page);
		return "/admin/users/list";
	}

	@RequestMapping("/view")
	public String view(Long id, ModelMap model) {
		User view = userService.get(id);
		model.put("view", view);
		model.put("roles", roleService.getAll());
		return "/admin/users/view";
	}

	@RequestMapping("/update_role")
	public String update(long id, String roleIds) {
		System.out.println(roleIds);
		String[] roleStrIds = roleIds.split(",");
		Long[] ids = new Long[roleStrIds.length];
		for(int i=0;i<ids.length;i++){
			ids[i] = Long.valueOf(roleStrIds[i]);
		}
		userService.updateRole(id, ids);
		return "redirect:/admin/users/list";
	}

	@RequestMapping(value = "/pwd", method = RequestMethod.GET)
	public String pwsView(Long id, ModelMap model) {
		User ret = userService.get(id);
		model.put("view", ret);
		return "/admin/users/pwd";
	}

	@RequestMapping(value = "/pwd", method = RequestMethod.POST)
	public String pwd(Long id, String newPassword, ModelMap model) {
		User ret = userService.get(id);
		model.put("view", ret);

		try {
			userService.updatePassword(id, newPassword);
			model.put("message", "修改成功");
		} catch (IllegalArgumentException e) {
			model.put("message", e.getMessage());
		}
		return "/admin/users/pwd";
	}

	@RequestMapping("/open")
	public @ResponseBody
	Data open(Long id) {
		userService.updateStatus(id, Consts.STATUS_NORMAL);
		Data data = Data.success("操作成功", Data.NOOP);
		return data;
	}

	@RequestMapping("/close")
	public @ResponseBody Data close(Long id) {
		userService.updateStatus(id, Consts.STATUS_CLOSED);
		Data data = Data.success("操作成功", Data.NOOP);
		return data;
	}
}
