/**
 * 
 */
package mblog.web.controller.browse;

import mblog.core.service.MblogService;
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
public class SearchController extends BaseController {
	@Autowired
	private MblogService mblogService;
	
	@RequestMapping("/search")
	public String search(Integer pageNo, String q, ModelMap model) {
		Paging paging = wrapPaging(pageNo);
		paging.setMaxResults(1);
		try {
			mblogService.search(paging, q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("paging", paging);
		model.put("q", q);
		return "/browse/search";
	}
	
}
