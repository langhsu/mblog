/**
 * 
 */
package mblog.web.controller.front.browse;

import java.util.List;

import mblog.core.pojos.Post;
import mblog.core.service.PostService;
import mblog.web.controller.front.BaseController;
import mtons.modules.pojos.UserContextHolder;
import mtons.modules.pojos.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/browse")
public class RecentController extends BaseController {
	@Autowired
	private PostService postService;
	
	@RequestMapping("/recents_json")
	public @ResponseBody List<Post> recent() {
		UserProfile up = UserContextHolder.getUserProfile();
		long ignoreUserId = 0;
		if (up != null) {
			ignoreUserId = up.getId();
		}
		List<Post> rets = postService.recents(8, ignoreUserId);
		return rets;
	}
	
}
