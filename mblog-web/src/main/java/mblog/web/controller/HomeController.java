/**
 * 
 */
package mblog.web.controller;

import mblog.core.service.PostService;
import mtons.modules.pojos.Page;
import mtons.modules.pojos.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author langhsu
 *
 */
@Controller
public class HomeController extends BaseController {
	@Autowired
	private PostService postService;
	
	@RequestMapping("/home")
	public String home(Integer pageNo, ModelMap model) {
		Page page = wrapPaging(pageNo);
		UserProfile profile = getProfile();
		postService.pagingByUserId(page,  profile.getId());
		
		model.put("page", page);
		
		return "/home";
	}
	
}
