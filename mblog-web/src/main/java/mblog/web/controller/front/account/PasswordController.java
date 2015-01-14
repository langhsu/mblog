/**
 * 
 */
package mblog.web.controller.front.account;

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
public class PasswordController extends BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public String view() {
		return getView(Views.ACCOUNT_PASSWORD);
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public String post(String oldPassword, String password, ModelMap model) {
		Data data = Data.failure("操作失败");
		try {
			userService.updatePassword(getProfile().getId(), oldPassword, password);
			
			data = Data.success("操作成功");
		} catch (Exception e) {
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return getView(Views.ACCOUNT_PASSWORD);
	}

}
