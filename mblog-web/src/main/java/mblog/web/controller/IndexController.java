/**
 * 
 */
package mblog.web.controller;

import mblog.core.planet.PostsPlanet;
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
public class IndexController extends BaseController{
	@Autowired
	private PostsPlanet postsPlanet;
	
	@RequestMapping("/")
	public String root(Integer pageNo, ModelMap model) {
		Paging paging = wrapPaging(pageNo);
		paging = postsPlanet.paging(paging);
		model.put("paging", paging);
		return "/index";
	}
	
	@RequestMapping("/index")
	public String index(Integer pageNo, ModelMap model) {
		Paging paging = wrapPaging(pageNo);
		paging = postsPlanet.paging(paging);
		model.put("paging", paging);
		return "/index";
	}
}
