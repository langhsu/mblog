/**
 * 
 */
package mblog.web.controller.browse;

import mblog.core.planet.PostsPlanet;
import mblog.core.pojos.Posts;
import mblog.core.service.PostsService;

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
@RequestMapping("/browse")
public class ViewController {
	@Autowired
	private PostsPlanet postsPlanet;
	@Autowired
	private PostsService postsService;
	
	@RequestMapping("/detail/{id}")
	public String view(@PathVariable Long id, ModelMap model) {
		Posts ret = postsPlanet.getPost(id);
		postsService.updateView(id);
		model.put("ret", ret);
		return "/browse/detail";
	}
}
