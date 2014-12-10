/**
 * 
 */
package mblog.web.controller.blog;

import java.io.File;
import java.util.Date;
import java.util.List;

import mblog.core.context.AppContext;
import mblog.core.pojos.Album;
import mblog.core.pojos.Mblog;
import mblog.core.service.MblogService;
import mblog.web.controller.BaseController;
import mtons.commons.utils.GMagickUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/blog")
public class BlogPostController extends BaseController {
	@Autowired
	private MblogService mblogService;
	@Autowired
	private AppContext appContext;
	
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String view(String type, ModelMap model) {
		model.put("type", type);
		return "/blog/post_" + type;
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String post(Mblog blog) {
		if (blog != null) {
			handleAlbums(blog.getAlbums());
			mblogService.add(blog);
		}
		return "redirect:/home";
	}

	private void handleAlbums(List<Album> albums) {
		if (albums == null) {
			return;
		}
		for (Album alb : albums) {
			createPic(alb);
		}
	}

	private void createPic(Album album) {
		String root = getRealPath("/");
		String originPath = root + appContext.getOriDir();
		String thumbsPath = root + appContext.getThumbsDir();

		Date current = new Date();
		String path = DateFormatUtils.format(current, "/yyyy/MMdd/");
		String fileName = DateFormatUtils.format(current, "yyyyMMddHms") + getSuffix(album.getOriginal());

		String rel = path + fileName;
		String dest = originPath + rel;
		String thumbs = thumbsPath + rel;

		File temp = new File(root + album.getOriginal());
		try {
			// 保存原图
			FileUtils.copyFile(temp, new File(dest));

			album.setOriginal(appContext.getOriDir() + rel);

			// 创建缩放图片
			GMagickUtils.scaleImage(temp.getAbsolutePath(), thumbs, 650);
			
			album.setPreview(appContext.getThumbsDir() + rel);
			
//			int[] wh = GMagickUtils.getSize(thumbs);
//			
//			if (wh != null && wh.length == 2) {
//				album.setWidth(wh[0]);
//				album.setHeight(wh[1]);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (temp != null) {
				temp.delete();
			}
		}
	}
	
}
