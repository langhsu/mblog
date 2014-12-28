/**
 * 
 */
package mblog.web.controller.browse;

import mblog.core.lang.Consts;
import mblog.core.planet.PostPlanet;
import mblog.core.pojos.Post;
import mblog.core.service.PostService;

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
	private PostPlanet postPlanet;
	@Autowired
	private PostService postService;
	
	@RequestMapping("/detail/{id}")
	public String view(@PathVariable Long id, ModelMap model) {
		Post ret = postPlanet.getPost(id);
		
		if (ret == null) {
			return "redirect:" + Consts.ERROR_PAGE_404;
		}
		
		postService.updateView(id);
		model.put("ret", ret);
		return "/browse/detail";
	}
}
