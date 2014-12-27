/**
 * 
 */
package mblog.web.controller.browse;

import mblog.core.planet.PostsPlanet;
import mblog.core.pojos.Posts;

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
public class ViewController {
	@Autowired
	private PostsPlanet postsPlanet;
	
	@RequestMapping("/detail/{id}")
	public String view(@PathVariable Long id, ModelMap model) {
		Posts ret = postsPlanet.getPost(id);
		model.put("ret", ret);
		return "/browse/detail";
	}
}
