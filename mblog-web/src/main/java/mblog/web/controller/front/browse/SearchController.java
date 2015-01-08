/**
 * 
 */
package mblog.web.controller.front.browse;

import mblog.core.service.PostService;
import mblog.core.service.TagService;
import mblog.web.controller.BaseController;
import mblog.web.controller.front.ViewPath;
import mtons.modules.pojos.Page;

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
	public String search(Integer pn, String q, ModelMap model) {
		Page page = wrapPage(pn);
		try {
			if (StringUtils.isNotEmpty(q)) {
				postService.search(page, q);
				tagService.updateHot(q);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("page", page);
		model.put("q", q);
		return getView(ViewPath.BROWSE_SEARCH);
	}
	
}
