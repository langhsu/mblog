/**
 * 
 */
package mblog.web.controller.browse;

import mblog.core.service.PostService;
import mblog.core.service.TagService;
import mblog.web.controller.BaseController;
import mtons.commons.pojos.Paging;

import org.apache.commons.lang.StringUtils;
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
public class SearchController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private TagService tagService;
	
	@RequestMapping("/search")
	public String search(Integer pageNo, String q, ModelMap model) {
		Paging paging = wrapPaging(pageNo);
		try {
			if (StringUtils.isNotEmpty(q)) {
				postService.search(paging, q);
				tagService.updateHot(q);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("paging", paging);
		model.put("q", q);
		return "/browse/search";
	}
	
}
