/**
 * 
 */
package hilo.web.controller.browse;

import hilo.core.pojos.Mblog;
import hilo.core.service.MblogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/browse")
public class BlogController {
	@Autowired
	private MblogService mblogService;
	
	@RequestMapping("/detail/{id}")
	public String view(@PathVariable Long id, ModelMap model) {
		Mblog ret = mblogService.get(id);
		model.put("ret", ret);
		return "/browse/detail";
	}
}
