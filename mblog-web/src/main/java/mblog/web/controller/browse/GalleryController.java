/**
 * 
 */
package mblog.web.controller.browse;

import mblog.core.service.PostsService;
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
@RequestMapping("/browse")
public class GalleryController extends BaseController {
	@Autowired
	private PostsService  postsService;
	
	private int jsonMaxResults = 8;
	
	@RequestMapping("/gallery")
	public String view(Integer pageNo, ModelMap model) {
		Paging paging = wrapPaging(pageNo);
		paging.setMaxResults(jsonMaxResults);
		postsService.paging(paging);
		model.put("paging", paging);
		return "/browse/gallery";
	}
	
	@RequestMapping("/gallery_snippet/{pageNo}")
	public String snippet(@PathVariable Integer pageNo, ModelMap model) {
		Paging paging = wrapPaging(pageNo);
		paging.setMaxResults(jsonMaxResults);
		postsService.paging(paging);
		model.put("paging", paging);
		return "/browse/snippet";
	}
}
