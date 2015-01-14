/**
 * 
 */
package mblog.web.controller.front;

import mblog.core.service.PostService;
import mblog.web.controller.BaseController;
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
	public String home(Integer pn, ModelMap model) {
		Page page = wrapPage(pn);
		UserProfile profile = getProfile();
		postService.pagingByUserId(page,  profile.getId());
		
		model.put("page", page);
		
		return getView(Views.HOME);
	}
	
}
