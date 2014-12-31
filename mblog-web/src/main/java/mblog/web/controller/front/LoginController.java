package mblog.web.controller.front;

import mblog.core.service.UserService;
import mtons.modules.pojos.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author langhsu
 *
 */
@Controller
public class LoginController extends BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String view() {
		return getView(ViewPath.LOGIN);
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password, ModelMap model) {
		UserProfile profile = userService.login(username, password);
		if (profile != null) {
			// record the login status
			putProfile(profile);
			return "redirect:" + ViewPath.HOME;
		} else {
			model.put("message", "用户名或密码错误");
		}
		return getView(ViewPath.LOGIN);
	}

}
