/**
 * 
 */
package hilo.web.controller.account;

import hilo.core.pojos.User;
import hilo.core.service.UserService;
import hilo.web.controller.BaseController;

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
	public String post(String nickname, String email) {
		User user = new User();
		user.setId(getProfile().getId());
		user.setEmail(email);
		user.setNickname(nickname);
		putProfile(userService.update(user));
		return "/account/profile";
	}

}
