/**
 * 
 */
package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.modules.entity.Permission;
import com.mtons.mblog.modules.entity.Role;
import com.mtons.mblog.modules.service.PermissionService;
import com.mtons.mblog.modules.service.RoleService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;

/**
 * @author - langhsu on 2018/2/11
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
	@Autowired
    private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@GetMapping("/list")
	public String paging(String name, ModelMap model) {
		Pageable pageable = wrapPageable();
		Page<Role> page = roleService.paging(pageable, name);
		model.put("name", name);
		model.put("page", page);
		return "/admin/role/list";
	}

	@RequestMapping("/view")
	public String view(Long id, ModelMap model) {
		if (id != null && id > 0) {
			Role role = roleService.get(id);
			model.put("view", role);
		}

        model.put("permissions", permissionService.tree());
        return "/admin/role/view";
	}
	
	@RequestMapping("/update")
	public String update(Role role, @RequestParam(value = "perms", required=false) List<Long> perms, ModelMap model) {
		Result data;

		HashSet<Permission> permissions = new HashSet<>();
		if(perms != null && perms.size() > 0){

            for(Long pid: perms){
                Permission p = new Permission();
                p.setId(pid);
				permissions.add(p);
            }
        }
        
        if (Role.ADMIN_ID == role.getId()) {
			data = Result.failure("管理员角色不可编辑");
        } else {
            roleService.update(role, permissions);
            data = Result.success();
        }
        model.put("data", data);
        return "redirect:/admin/role/list";
	}
	
	@RequestMapping("/activate")
	@ResponseBody
	public Result activate(Long id, Boolean active) {
		Result ret = Result.failure("操作失败");
		if (id != null && id != Role.ADMIN_ID) {
			roleService.activate(id, active);
			ret = Result.success();
		}
		return ret;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(@RequestParam("id") Long id) {
		Result ret;
		if (Role.ADMIN_ID == id) {
			ret = Result.failure("管理员不能操作");
        }else if(roleService.delete(id)){
        	ret = Result.success();
        }else{
        	ret = Result.failure("该角色不能被操作");
        }
		return ret;
	}
}
