/**
 * 
 */
package com.mtons.mblog.web.controller.site.auth;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.service.SecurityCodeService;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
			return "redirect:/home";
		}
		return view(Views.REGISTER);
	}
	
	@PostMapping("/register")
	public String register(UserVO post, ModelMap model) {
		Result data;
		String ret = view(Views.REGISTER);

		try {
			post.setAvatar(Consts.AVATAR);
			userService.register(post);
			data = Result.successMessage("恭喜您! 注册成功");
			data.addLink("login", "前往登录");
			ret = view(Views.REGISTER_RESULT);
		} catch (Exception e) {
            model.addAttribute("post", post);
			data = Result.failure(e.getMessage());
		}
		model.put("data", data);
		return ret;
	}

}