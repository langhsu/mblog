/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.site.auth;

import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.service.MessageService;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录页
 * @author langhsu
 */
@Slf4j
@Controller
public class LoginController extends BaseController {
    @Autowired
    private MessageService messageService;

    /**
     * 跳转登录页
     * @return
     */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String view() {
		return view(Views.LOGIN);
	}

    /**
     * 提交登录
     * @param username
     * @param password
     * @param model
     * @return
     */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password,@RequestParam(value = "rememberMe",defaultValue = "0") int rememberMe, ModelMap model) {
		String ret = view(Views.LOGIN);
		
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ret;
        }

        UsernamePasswordToken token = createToken(username, password);
        if (token == null) {
        	model.put("message", "用户名或密码错误");
            return ret;
        }

        if (rememberMe == 1) {
            token.setRememberMe(true);
        }

        try {
            SecurityUtils.getSubject().login(token);
            AccountProfile profile = getProfile();
            ret = String.format(Views.REDIRECT_USER_HOME, profile.getId());
        } catch (UnknownAccountException e) {
            log.error(e.getMessage());
            throw new MtonsException("用户不存在");
        } catch (LockedAccountException e) {
            log.error(e.getMessage());
            throw new MtonsException("用户被禁用");
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new MtonsException("用户认证失败");
        }
        return ret;
	}

}
