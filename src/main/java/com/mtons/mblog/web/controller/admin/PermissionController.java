package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.modules.data.PermissionTree;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.modules.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author - langhsu
 * @create - 2018/5/18
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionController extends BaseController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/tree")
    @ResponseBody
    public List<PermissionTree> tree() {
        return permissionService.tree();
    }
}
