package com.mtons.mblog.web.menu;

import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.entity.Role;
import com.mtons.mblog.modules.template.TemplateDirective;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by langhsu on 2017/11/21.
 */
@Component
public class MenusDirective extends TemplateDirective {
    @Override
    public String getName() {
        return "menus";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        List<Menu> menus = filterMenu(SecurityUtils.getSubject());
        handler.put(RESULTS, menus).render();
    }

    private List<Menu> filterMenu(Subject subject) {
        List<Menu> menus = MenuJsonUtils.getMenus();
        if (!subject.hasRole(Role.ROLE_ADMIN)) {
            menus = check(subject, menus);
        }
        return menus;
    }

    private List<Menu> check(Subject subject, List<Menu> menus) {
        List<Menu> results = new LinkedList<>();
        for (Menu menu : menus) {
            if (check(subject, menu)) {
                results.add(menu);
            }
        }

        return results;
    }

    private boolean check(Subject subject, Menu menu) {
        boolean authorized = false;
        if (StringUtils.isBlank(menu.getPermission())) {
            authorized = true;
        } else {
            for(String perm : menu.getPermission().split(",")){
                if(subject.isPermitted(perm)){
                    authorized = true;
                    break;
                }
            }
        }
        return authorized;
    }

}
