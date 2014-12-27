/**
 * 
 */
package mblog.web.controller.browse;

import mblog.core.service.PostService;
import mblog.web.controller.BaseController;
import mtons.commons.pojos.Paging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/browse")
public class ExploreController extends BaseController {
	@Autowired
	private PostService postService;
	
	@RequestMapping("/explore")
	public String view(Integer pageNo, ModelMap model) {
		Paging paging = wrapPaging(pageNo);
		postService.paging(paging);
		model.put("paging", paging);
		return "/browse/explore";
	}
	
}
