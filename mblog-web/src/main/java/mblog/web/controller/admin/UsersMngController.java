/**
 * 
 */
package mblog.web.controller.admin;

import mblog.core.service.UserService;
import mblog.web.controller.front.BaseController;
import mtons.modules.pojos.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/admin/users")
public class UsersMngController extends BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	public String list(Integer pn, ModelMap model) {
		Page page = wrapPage(pn);
		userService.paging(page);
		model.put("page", page);
		return "/admin/users/list";
	}
}
