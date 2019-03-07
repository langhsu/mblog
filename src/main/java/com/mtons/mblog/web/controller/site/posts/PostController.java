/**
 *
 */
package com.mtons.mblog.web.controller.site.posts;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.service.ChannelService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * 文章操作
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/post")
public class PostController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private ChannelService channelService;

	/**
	 * 发布文章页
	 * @return
	 */
	@GetMapping("/editing")
	public String view(Long id, ModelMap model) {
		model.put("channels", channelService.findAll(Consts.STATUS_NORMAL));
		model.put("editing", true);
		String editor = siteOptions.getValue("editor");
		if (null != id && id > 0) {
			AccountProfile profile = getProfile();
			PostVO view = postService.get(id);

			Assert.notNull(view, "该文章已被删除");
			Assert.isTrue(view.getAuthorId() == profile.getId(), "该文章不属于你");

			Assert.isTrue(view.getChannel().getStatus() == Consts.STATUS_NORMAL, "请在后台编辑此文章");
			model.put("view", view);

			if (StringUtils.isNoneBlank(view.getEditor())) {
				editor = view.getEditor();
			}
		}
		model.put("editor", editor);
		return view(Views.POST_EDITING);
	}

	/**
	 * 提交发布
	 * @param post
	 * @return
	 */
	@PostMapping("/submit")
	public String post(PostVO post) {
		Assert.notNull(post, "参数不完整");
		Assert.state(StringUtils.isNotBlank(post.getTitle()), "标题不能为空");
		Assert.state(StringUtils.isNotBlank(post.getContent()), "内容不能为空");

		AccountProfile profile = getProfile();
		post.setAuthorId(profile.getId());

		// 修改时, 验证归属
		if (post.getId() > 0) {
			PostVO exist = postService.get(post.getId());
			Assert.notNull(exist, "文章不存在");
			Assert.isTrue(exist.getAuthorId() == profile.getId(), "该文章不属于你");

			postService.update(post);
		} else {
			postService.post(post);
		}
		return String.format(Views.REDIRECT_USER_HOME, profile.getId());
	}

	/**
	 * 删除文章
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable Long id) {
		Result data;
		try {
			postService.delete(id, getProfile().getId());
			data = Result.success();
		} catch (Exception e) {
			data = Result.failure(e.getMessage());
		}
		return data;
	}

}
