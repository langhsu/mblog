/**
 * 
 */
package com.mtons.mblog.web.controller.site.auth;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.service.SecurityCodeService;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.base.data.Data;
import com.mtons.mblog.base.utils.MailHelper;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.ExecutorService;

/**
 * @author langhsu
 *
 */
@Controller
public class RegisterController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityCodeService securityCodeService;
	@Autowired
	private MailHelper mailHelper;
	@Autowired
	private ExecutorService executorService;
	
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
		Data data;
		String ret = view(Views.REGISTER);

		try {
			post.setAvatar(Consts.AVATAR);
			userService.register(post);
			data = Data.success("恭喜您! 注册成功", Data.NOOP);
			data.addLink("login", "前往登录");
			ret = view(Views.REGISTER_RESULT);
		} catch (Exception e) {
            model.addAttribute("post", post);
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return ret;
	}

}