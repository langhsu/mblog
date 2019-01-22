/**
 * 
 */
package com.mtons.mblog.web.controller.api;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/api")
public class PostJsonController extends BaseController {
	@Autowired
	private PostService postService;
	
	@RequestMapping("/posts")
	public @ResponseBody
	Page<PostVO> posts(HttpServletRequest request) {
		String order = ServletRequestUtils.getStringParameter(request, "ord", Consts.order.NEWEST);
		int channelId = ServletRequestUtils.getIntParameter(request, "channelId", 0);
		Pageable pageable = wrapPageable();
		Page<PostVO> page = postService.paging(pageable, channelId, null, order);
		return page;
	}
}
