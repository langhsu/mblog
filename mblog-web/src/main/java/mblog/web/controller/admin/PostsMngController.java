/**
 * 
 */
package mblog.web.controller.admin;

import mblog.core.planet.PostPlanet;
import mblog.core.pojos.Post;
import mblog.core.service.PostService;
import mblog.web.controller.BaseController;
import mtons.modules.pojos.Data;
import mtons.modules.pojos.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/admin/posts")
public class PostsMngController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private PostPlanet postPlanet;
	
	@RequestMapping("/list")
	public String list(Integer pn, ModelMap model) {
		Page page = wrapPage(pn);
		postService.paging(page);
		model.put("page", page);
		return "/admin/posts/list";
	}
	
	@RequestMapping("/view")
	public String view(Long id, ModelMap model) {
		Post ret = postService.get(id);
		model.put("view", ret);
		return "/admin/posts/view";
	}
	
	@RequestMapping("/delete")
	public @ResponseBody Data delete(Long id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			try {
				postPlanet.delete(id);
				data = Data.success("操作成功");
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
}
