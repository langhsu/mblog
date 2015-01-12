/**
 * 
 */
package mblog.web.controller.front.comment;

import mblog.core.pojos.Comment;
import mblog.core.service.CommentService;
import mblog.web.controller.BaseController;
import mtons.modules.pojos.Data;
import mtons.modules.pojos.Page;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping("/list/{toId}")
	public @ResponseBody Page view(Integer pn, @PathVariable Long toId) {
		Page page = wrapPage(pn);
		commentService.paging(page, toId);
		return page;
	}
	
	@RequestMapping("/j_post")
	public @ResponseBody Data post(Long toId, String text) {
		Data data = Data.failure("操作失败");
		if (toId > 0 && StringUtils.isNotEmpty(text)) {
			Comment c = new Comment();
			c.setToId(toId);
			c.setContent(text);
			commentService.post(c);
			data = Data.success("发表成功!");
		}
		return data;
	}
	
}
