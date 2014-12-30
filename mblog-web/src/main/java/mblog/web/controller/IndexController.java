/**
 * 
 */
package mblog.web.controller;

import mblog.core.planet.PostPlanet;
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
public class IndexController extends BaseController{
	@Autowired
	private PostPlanet postPlanet;
	
	@RequestMapping("/")
	public String root(Integer pageNo, ModelMap model) {
		Page page = wrapPaging(pageNo);
		page = postPlanet.paging(page);
		model.put("page", page);
		return "/index";
	}
	
	@RequestMapping("/index")
	public String index(Integer pageNo, ModelMap model) {
		Page page = wrapPaging(pageNo);
		page = postPlanet.paging(page);
		model.put("page", page);
		return "/index";
	}
}
