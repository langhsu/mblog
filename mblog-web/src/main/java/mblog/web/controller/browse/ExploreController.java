/**
 * 
 */
package mblog.web.controller.browse;

import java.util.List;

import mblog.core.planet.TagPlanet;
import mblog.core.pojos.Tag;
import mblog.core.service.PostService;
import mblog.core.service.TagService;
import mblog.web.controller.BaseController;
import mtons.commons.pojos.Paging;

import org.apache.commons.lang.StringUtils;
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
public class ExploreController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private TagPlanet tagPlanet;
	@Autowired
	private TagService tagService;
	
	private int maxResults = 12;
	
	@RequestMapping("/explore")
	public String view(ModelMap model) {
		List<Tag> tags = tagPlanet.topTags(maxResults, true);
		model.put("tags", tags);
		return "/browse/explore";
	}
	
	@RequestMapping("/tag/{tag}")
	public String tag(@PathVariable String tag, Integer pageNo, ModelMap model) {
		Paging paging = wrapPaging(pageNo);
		try {
			if (StringUtils.isNotEmpty(tag)) {
				postService.searchByTag(paging, tag);
				tagService.updateHot(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("paging", paging);
		model.put("tag", tag);
		return "/browse/tag";
	}
	
}
