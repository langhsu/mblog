/**
 * 
 */
package mblog.web.controller.browse;

import java.util.List;

import mblog.core.pojos.Posts;
import mblog.core.service.PostsService;
import mblog.web.controller.BaseController;
import mtons.commons.pojos.UserContextHolder;
import mtons.commons.pojos.UserProfile;

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
	private PostsService postsService;
	
	@RequestMapping("/recents_json")
	public @ResponseBody List<Posts> recent() {
		UserProfile up = UserContextHolder.getUserProfile();
		long ignoreUserId = 0;
		if (up != null) {
			ignoreUserId = up.getId();
		}
		List<Posts> rets = postsService.recents(8, ignoreUserId);
		return rets;
	}
	
}
