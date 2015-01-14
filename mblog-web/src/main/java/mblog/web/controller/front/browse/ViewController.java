/**
 * 
 */
package mblog.web.controller.front.browse;

import mblog.core.lang.Consts;
import mblog.core.planet.PostPlanet;
import mblog.core.pojos.Post;
import mblog.core.service.PostService;
import mblog.web.controller.BaseController;
import mblog.web.controller.front.Views;

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
public class ViewController extends BaseController {
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
		
		postService.identityViews(id);
		model.put("ret", ret);
		return getView(Views.BROWSE_DETAIL);
	}
}
