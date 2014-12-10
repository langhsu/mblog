/**
 * 
 */
package hilo.web.controller.browse;

import hilo.core.pojos.Mblog;
import hilo.core.service.MblogService;
import hilo.web.controller.BaseController;

import java.util.List;

import mtons.commons.pojos.UserContextHolder;
import mtons.commons.pojos.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/browse")
public class RecentController extends BaseController {
	@Autowired
	private MblogService mblogService;
	
	@RequestMapping("/recents_json")
	public @ResponseBody List<Mblog> recent() {
		UserProfile up = UserContextHolder.getUserProfile();
		long ignoreUserId = 0;
		if (up != null) {
			ignoreUserId = up.getId();
		}
		List<Mblog> rets = mblogService.recents(8, ignoreUserId);
		return rets;
	}
	
}
