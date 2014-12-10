/**
 * 
 */
package mblog.web.controller.account;

import mblog.core.service.UserService;
import mblog.web.controller.BaseController;

import org.apache.commons.lang.StringUtils;
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
public class PasswordController extends BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public String view() {
		return "/account/password";
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public String post(String password) {
		if (StringUtils.isNotEmpty(password)) {
			userService.updatePassword(getProfile().getId(), password);
		}
		return "/account/password";
	}

}
