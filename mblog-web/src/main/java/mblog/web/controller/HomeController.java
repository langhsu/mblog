/**
 * 
 */
package mblog.web.controller;

import mblog.core.service.PostService;
import mtons.commons.pojos.Paging;
import mtons.commons.pojos.UserProfile;

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
		Paging paging = wrapPaging(pageNo);
		UserProfile profile = getProfile();
		postService.pagingByUserId(paging,  profile.getId());
		
		model.put("paging", paging);
		
		return "/home";
	}
	
}
