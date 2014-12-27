/**
 * 
 */
package mblog.web.controller.user;

import mblog.core.planet.PostPlanet;
import mblog.core.pojos.User;
import mblog.core.service.UserService;
import mblog.web.controller.BaseController;
import mtons.commons.pojos.Paging;

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
	public String home(@PathVariable Long uid, Integer pageNo, ModelMap model) {
		User user = userService.get(uid);
		Paging paging = wrapPaging(pageNo);
		paging = postPlanet.pagingByUserId(paging, uid);
		
		model.put("user", user);
		model.put("paging", paging);
		return "/user/home";
	}
}
