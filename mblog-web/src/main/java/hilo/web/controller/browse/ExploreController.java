/**
 * 
 */
package hilo.web.controller.browse;

import hilo.core.service.MblogService;
import hilo.web.controller.BaseController;
import mtons.commons.pojos.Paging;

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
@RequestMapping("/browse")
public class ExploreController extends BaseController {
	@Autowired
	private MblogService mblogService;
	private int projectId = 1;
	
	@RequestMapping("/explore")
	public String view(Integer pageNo, ModelMap model) {
		Paging paging = wrapPaging(pageNo);
		mblogService.paging(paging, projectId);
		model.put("paging", paging);
		return "/browse/explore";
	}
	
	@RequestMapping("/explore_json")
	public @ResponseBody Paging ajax(Integer pageNo) {
		Paging paging = wrapPaging(pageNo);
		mblogService.paging(paging, projectId);
		return paging;
	}
	
}
