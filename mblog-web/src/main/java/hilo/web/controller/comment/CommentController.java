/**
 * 
 */
package hilo.web.controller.comment;

import hilo.core.pojos.Comment;
import hilo.core.service.CommentService;
import hilo.web.controller.BaseController;
import mtons.commons.pojos.Data;
import mtons.commons.pojos.Paging;

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
	public @ResponseBody Paging view(Integer pageNo, Integer projectId, long contentId) {
		if (projectId == null) {
			projectId = 1;
		}
		Paging paging = wrapPaging(pageNo);
		commentService.paging(paging, projectId, contentId);
		return paging;
	}
	
	@RequestMapping("/j_post")
	public @ResponseBody Data post(Integer projectId, Long contentId, String content) {
		Data data = Data.failure("failure");
		if (contentId > 0 && StringUtils.isNotEmpty(content)) {
			Comment c = new Comment();
			c.setProjectId(projectId);
			c.setToId(contentId);
			c.setContent(content);
			commentService.post(c);
			data = Data.success("success");
		}
		return data;
	}
	
}
