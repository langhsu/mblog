/**
 * 
 */
package mblog.web.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/account")
public class AvaterController {
	
	@RequestMapping(value = "/avater", method = RequestMethod.GET)
	public String view() {
		return "/account/avater";
	}
	
	@RequestMapping(value = "/avater", method = RequestMethod.POST)
	public String post(String path, ModelMap model) {
		return "redirect:/account/profile";
	}
}
