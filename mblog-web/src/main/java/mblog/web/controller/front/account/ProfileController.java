/**
 * 
 */
package mblog.web.controller.front.account;

import mblog.core.pojos.User;
import mblog.core.service.UserService;
import mblog.web.controller.BaseController;
import mblog.web.controller.front.Views;
import mtons.modules.pojos.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
		return getView(Views.ACCOUNT_PROFILE);
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String post(String name, String email, ModelMap model) {
		Data data = Data.failure("注册失败");
		try {
			model.put("data", data);
			User user = new User();
			user.setId(getProfile().getId());
			user.setEmail(email);
			user.setName(name);
			putProfile(userService.update(user));
			
			data = Data.success("操作成功");
		} catch (Exception e) {
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return getView(Views.ACCOUNT_PROFILE);
	}

}
