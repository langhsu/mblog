/**
 * 
 */
package mblog.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@RequestMapping("/index")
	public String index(Integer pn, ModelMap model) {
		return "/admin/index";
	}
}
