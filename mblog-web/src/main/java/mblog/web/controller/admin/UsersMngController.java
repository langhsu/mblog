/**
 * 
 */
package mblog.web.controller.admin;

import mblog.core.pojos.User;
import mblog.core.service.UserService;
import mblog.web.controller.BaseController;
import mtons.modules.lang.Const;
import mtons.modules.pojos.Data;
import mtons.modules.pojos.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping(value = "/pwd", method = RequestMethod.GET)
	public String pwsView(Long id, ModelMap model) {
		User ret = userService.get(id);
		model.put("view", ret);
		return "/admin/users/pwd";
	}
	
	@RequestMapping(value = "/pwd", method = RequestMethod.POST)
	public String pwd(Long id, String newPassword, ModelMap model) {
		User ret = userService.get(id);
		model.put("view", ret);

		try {
			userService.updatePassword(id, newPassword);
			model.put("message", "修改成功");
		} catch (IllegalArgumentException e) {
			model.put("message", e.getMessage());
		}
		return "/admin/users/pwd";
	}
	
	@RequestMapping("/open")
	public @ResponseBody Data open(Long id) {
		userService.updateStatus(id, Const.STATUS_NORMAL);
		Data data = Data.success("操作成功");
		return data;
	}
	
	@RequestMapping("/close")
	public @ResponseBody Data close(Long id) {
		userService.updateStatus(id, Const.STATUS_CLOSED);
		Data data = Data.success("操作成功");
		return data;
	}
}
