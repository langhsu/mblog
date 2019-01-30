/**
 * 
 */
package com.mtons.mblog.web.controller.site.comment;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.event.MessageEvent;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.modules.data.CommentVO;
import com.mtons.mblog.modules.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/comment")
@ConditionalOnProperty(name = "site.controls.comment", havingValue = "true", matchIfMissing = true)
public class CommentController extends BaseController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping("/list/{toId}")
	public @ResponseBody Page<CommentVO> view(@PathVariable Long toId) {
		Pageable pageable = wrapPageable();
		return commentService.pagingByPostId(pageable, toId);
	}
	
	@RequestMapping("/submit")
	public @ResponseBody
	Result post(Long toId, String text, HttpServletRequest request) {
		Result data = Result.failure("操作失败");
		
		long pid = ServletRequestUtils.getLongParameter(request, "pid", 0);
		
		if (!isAuthenticated()) {
			data = Result.failure("请先登录在进行操作");
			
			return data;
		}
		if (toId > 0 && StringUtils.isNotEmpty(text)) {
			AccountProfile up = getProfile();
			
			CommentVO c = new CommentVO();
			c.setToId(toId);
			c.setContent(HtmlUtils.htmlEscape(text));
			c.setAuthorId(up.getId());
			
			c.setPid(pid);
			
			commentService.post(c);

            if(toId != up.getId()) {
			    sendMessage(up.getId(), toId, pid);
            }
			
			data = Result.successMessage("发表成功!");
		}
		return data;
	}

	@RequestMapping("/delete")
	public @ResponseBody
	Result delete(Long id) {
		Result data = Result.failure("操作失败");
		if (id != null) {
			AccountProfile up = getProfile();
			try {
				commentService.delete(id, up.getId());
				data = Result.success();
			} catch (Exception e) {
				data = Result.failure(e.getMessage());
			}
		}
		return data;
	}

	/**
	 * 发送通知
	 * @param userId
	 * @param postId
	 */
	private void sendMessage(long userId, long postId, long pid) {
		MessageEvent event = new MessageEvent("MessageEvent");
		event.setFromUserId(userId);

		if (pid > 0) {
			event.setEvent(Consts.MESSAGE_EVENT_COMMENT_REPLY);
		} else {
			event.setEvent(Consts.MESSAGE_EVENT_COMMENT);
		}
		// 此处不知道文章作者, 让通知事件系统补全
		event.setPostId(postId);
		applicationContext.publishEvent(event);
	}
}