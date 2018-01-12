/**
 *
 */
package mblog.web.controller.desk.posts;

import mblog.base.data.Data;
import mblog.base.lang.Consts;
import mblog.core.data.AccountProfile;
import mblog.core.data.Post;
import mblog.core.persist.service.ChannelService;
import mblog.core.persist.service.PostService;
import mblog.web.controller.BaseController;
import mblog.web.controller.desk.Views;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String view(ModelMap model) {
		model.put("groups", channelService.findAll(Consts.STATUS_NORMAL));
		return view(Views.ROUTE_POST_PUBLISH);
	}

	/**
	 * 提交发布
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String post(Post p, HttpServletRequest request) {

		if (p != null && StringUtils.isNotBlank(p.getTitle())) {
			String content = request.getParameter("content");
			AccountProfile profile = getSubject().getProfile();

			p.setContent(content);
			p.setAuthorId(profile.getId());

			postService.post(p);
		}
		return Views.REDIRECT_USER_POSTS;
	}

	/**
	 * 删除文章
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public @ResponseBody
	Data delete(@PathVariable Long id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			AccountProfile up = getSubject().getProfile();
			try {
				postService.delete(id, up.getId());
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}

	/**
	 * 修改文章
	 * @param id
	 * @return
	 */
	@RequestMapping("/to_update/{id}")
	public String toUpdate(@PathVariable Long id, ModelMap model) {
		AccountProfile up = getSubject().getProfile();
		Post ret = postService.get(id);

		Assert.notNull(ret, "该文章已被删除");

		Assert.isTrue(ret.getAuthorId() == up.getId(), "该文章不属于你");

		model.put("groups", channelService.findAll(Consts.STATUS_NORMAL));
		model.put("view", ret);
		return view(Views.ROUTE_POST_UPDATE);
	}

	/**
	 * 更新文章方法
	 * @author LBB
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String subUpdate(Post p, HttpServletRequest request) {
		AccountProfile up = getSubject().getProfile();
		if (p != null && p.getAuthorId() == up.getId()) {
			String content = request.getParameter("content");
			p.setContent(content);
			postService.update(p);
		}
		return Views.REDIRECT_USER_POSTS;
	}

}
