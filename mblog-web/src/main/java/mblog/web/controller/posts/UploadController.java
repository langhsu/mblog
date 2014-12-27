/**
 * 
 */
package mblog.web.controller.posts;

import java.io.File;
import java.io.IOException;

import mblog.core.context.AppContext;
import mblog.web.controller.BaseController;
import mtons.commons.pojos.Data;
import mtons.commons.utils.GMagickUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/blog")
public class UploadController extends BaseController {
	@Autowired
	private AppContext appContext;
	
	@RequestMapping("/upload")
    public @ResponseBody Data upload(@RequestParam(value = "file", required=false) MultipartFile file,
    		Boolean scale, Integer size) {
    	Data data = Data.failure("error");
    	
    	if (file.isEmpty()) {
			return data;
		}
    	String root = "/";
    	String realpath = getRealPath(root);
    	try {
			String path = copyFile(root, appContext.getTempDir(), file);
			
			if (scale != null && scale == true) {
				File transFile = new File(realpath + path);
				String fn = "/" + genFileName(file);
				String t = realpath + appContext.getTempDir() + fn;
				try {
					GMagickUtils.scaleImageByWidth(transFile.getAbsolutePath(), t, size);
					path = appContext.getTempDir() + fn;
					
					transFile.delete();
				} catch (Exception e) {
					data = Data.failure("文件保存失败");
				}
			}
			data = Data.success("", path);
		} catch (IOException e) {
			//FIXME: error handle
		}
    	
    	return data;
    }
    
}
