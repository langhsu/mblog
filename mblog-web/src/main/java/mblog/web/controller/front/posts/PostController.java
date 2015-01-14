/**
 * 
 */
package mblog.web.controller.front.posts;

import java.io.File;
import java.util.List;

import mblog.core.context.AppContext;
import mblog.core.planet.PostPlanet;
import mblog.core.pojos.Attach;
import mblog.core.pojos.Post;
import mblog.core.service.PostService;
import mblog.web.controller.BaseController;
import mblog.web.controller.front.Views;
import mblog.web.upload.Repository;
import mtons.modules.pojos.Data;
import mtons.modules.pojos.UserContextHolder;
import mtons.modules.pojos.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/blog")
public class PostController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private PostPlanet postPlanet;
	@Autowired
	private AppContext appContext;
	@Autowired
	private Repository fileRepository;
	
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String view(String type, ModelMap model) {
		model.put("type", type);
		return getView(Views.BLOG_POST + type);
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String post(Post blog) {
		if (blog != null) {
			handleAlbums(blog.getAlbums());
			postService.post(blog);
		}
		return "redirect:/home";
	}
	
	@RequestMapping("/delete/{id}")
	public @ResponseBody Data delete(@PathVariable Long id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			UserProfile up = UserContextHolder.getUserProfile();
			try {
				postPlanet.delete(id, up.getId());
				data = Data.success("操作成功");
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
	
	@RequestMapping("/heart")
	public @ResponseBody Data heart(Long id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			try {
				postService.identityHearts(id);
				data = Data.success("操作成功,感谢您的支持!");
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
	
	private void handleAlbums(List<Attach> albums) {
		if (albums == null) {
			return;
		}
		for (Attach alb : albums) {
			createPic(alb);
		}
	}

	private void createPic(Attach album) {
		String root = getRealPath("/");
		File temp = new File(root + album.getOriginal());
		
		try {
			// 保存原图
			String orig = fileRepository.storeScale(temp, appContext.getOrigDir(), 700);
			album.setOriginal(orig);
			
			// 创建缩放图片
			String preview = fileRepository.storeScale(temp, appContext.getThumbsDir(), 360);
			album.setPreview(preview);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (temp != null) {
				temp.delete();
			}
		}
	}
	
}
