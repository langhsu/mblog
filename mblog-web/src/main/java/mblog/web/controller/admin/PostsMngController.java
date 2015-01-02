/**
 * 
 */
package mblog.web.controller.admin;

import mblog.core.service.PostService;
import mblog.web.controller.front.BaseController;
import mtons.modules.pojos.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/admin/posts")
public class PostsMngController extends BaseController {
	@Autowired
	private PostService postService;
	
	@RequestMapping("/list")
	public String list(Integer pn, ModelMap model) {
		Page page = wrapPage(pn);
		postService.paging(page);
		model.put("page", page);
		return "/admin/posts/list";
	}
}
