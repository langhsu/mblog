/**
 * 
 */
package mblog.web.controller.front.user;

import mblog.core.planet.PostPlanet;
import mblog.core.pojos.User;
import mblog.core.service.UserService;
import mblog.web.controller.BaseController;
import mblog.web.controller.front.ViewPath;
import mtons.modules.pojos.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author langhsu
 *
 */
@Controller
public class UserHomeController extends BaseController {
	@Autowired
	private PostPlanet postPlanet;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/{uid}")
	public String home(@PathVariable Long uid, Integer pn, ModelMap model) {
		User user = userService.get(uid);
		Page page = wrapPage(pn);
		page = postPlanet.pagingByUserId(page, uid);
		
		model.put("user", user);
		model.put("page", page);
		return getView(ViewPath.USER_HOME);
	}
}
