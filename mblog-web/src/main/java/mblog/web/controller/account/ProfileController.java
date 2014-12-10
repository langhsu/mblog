/**
 * 
 */
package mblog.web.controller.account;

import mblog.core.pojos.User;
import mblog.core.service.UserService;
import mblog.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/account")
public class ProfileController extends BaseController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String view() {
		return "/account/profile";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String post(String name, String email) {
		User user = new User();
		user.setId(getProfile().getId());
		user.setEmail(email);
		user.setName(name);
		putProfile(userService.update(user));
		return "/account/profile";
	}

}
