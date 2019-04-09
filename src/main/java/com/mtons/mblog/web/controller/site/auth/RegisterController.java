/**
 * 
 */
package com.mtons.mblog.web.controller.site.auth;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.service.SecurityCodeService;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author langhsu
 *
 */
@Controller
@ConditionalOnProperty(name = "site.controls.register", havingValue = "true", matchIfMissing = true)
public class RegisterController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityCodeService securityCodeService;

	@GetMapping("/register")
	public String view() {
		AccountProfile profile = getProfile();
		if (profile != null) {
			return String.format(Views.REDIRECT_USER_HOME, profile.getId());
		}
		return view(Views.REGISTER);
	}
	
	@PostMapping("/register")
	public String register(UserVO post, HttpServletRequest request, ModelMap model) {
		String view = view(Views.REGISTER);
		try {
			if (siteOptions.getControls().isRegister_email_validate()) {
				String code = request.getParameter("code");
				Assert.state(StringUtils.isNotBlank(post.getEmail()), "请输入邮箱地址");
				Assert.state(StringUtils.isNotBlank(code), "请输入邮箱验证码");
				securityCodeService.verify(post.getEmail(), Consts.CODE_REGISTER, code);
			}
			post.setAvatar(Consts.AVATAR);
			userService.register(post);
			Result<AccountProfile> result = executeLogin(post.getUsername(), post.getPassword(), false);
			view = String.format(Views.REDIRECT_USER_HOME, result.getData().getId());
		} catch (Exception e) {
            model.addAttribute("post", post);
			model.put("data", Result.failure(e.getMessage()));
		}
		return view;
	}

}