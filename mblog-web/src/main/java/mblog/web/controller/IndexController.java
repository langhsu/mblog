/**
 * 
 */
package mblog.web.controller;

import mblog.core.service.MblogService;

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
	private MblogService mblogService;
	
	@RequestMapping("/")
	public String root() {
		if (getProfile() != null) {
			return "redirect:/home";
		}
		return "/index";
	}
	
	@RequestMapping("/index")
	public String index(Integer pageNo, ModelMap model) {
		if (getProfile() != null) {
			return "redirect:/home";
		}
		
		return "/index";
	}
}
