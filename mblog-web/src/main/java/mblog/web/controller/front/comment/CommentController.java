/**
 * 
 */
package mblog.web.controller.front.comment;

import mblog.core.pojos.Comment;
import mblog.core.service.CommentService;
import mblog.web.controller.front.BaseController;
import mtons.modules.pojos.Data;
import mtons.modules.pojos.Page;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("/list")
	public @ResponseBody Page view(Integer pn, long contentId) {
		Page page = wrapPage(pn);
		commentService.paging(page, contentId);
		return page;
	}
	
	@RequestMapping("/j_post")
	public @ResponseBody Data post(Long contentId, String content) {
		Data data = Data.failure("failure");
		if (contentId > 0 && StringUtils.isNotEmpty(content)) {
			Comment c = new Comment();
			c.setToId(contentId);
			c.setContent(content);
			commentService.post(c);
			data = Data.success("success");
		}
		return data;
	}
	
}
