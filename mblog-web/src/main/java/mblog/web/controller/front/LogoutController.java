/**
 * 
 */
package mblog.web.controller.front;

import mblog.web.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author langhsu
 *
 */
@Controller
public class LogoutController extends BaseController {

	@RequestMapping("/logout")
	public String logout() {
		putProfile(null);
		return "redirect:/login";
	}

}
