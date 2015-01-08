/**
 * 
 */
package mblog.web.controller.front.account;

import mblog.core.lang.Consts;
import mblog.core.pojos.User;
import mblog.core.service.UserService;
import mblog.web.controller.BaseController;
import mblog.web.controller.front.ViewPath;
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
public class RegController extends BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String view(ModelMap model) {
		if (getProfile() != null) {
			return "redirect:/home";
		}
		return getView(ViewPath.REG);
	}
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String reg(User user, ModelMap model) {
		Data data = Data.failure("注册失败");
		String ret = getView(ViewPath.REG);
		
		try {
			user.setAvater(Consts.avater);
			userService.register(user);
			data = Data.success("恭喜您! 注册成功");
			ret = getView(ViewPath.REG_RESULT);
			
		} catch (Exception e) {
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return ret;
	}

}
