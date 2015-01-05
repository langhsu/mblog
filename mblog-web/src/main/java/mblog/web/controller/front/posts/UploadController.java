/**
 * 
 */
package mblog.web.controller.front.posts;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mblog.core.context.AppContext;
import mblog.web.controller.front.BaseController;
import mtons.modules.pojos.Data;
import mtons.modules.utils.GMagickUtils;

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

	private static Map<String, String> errors = new HashMap<String, String>();
	
	// 文件允许格式
	private String[] allowFiles = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
	
	static {
		errors.put("SUCCESS", "SUCCESS"); //默认成功
		errors.put("NOFILE", "未包含文件上传域");
		errors.put("TYPE", "不允许的文件格式");
		errors.put("SIZE", "文件大小超出限制");
		errors.put("REQUEST", "上传请求异常");
		errors.put("UNKNOWN", "未知错误");
	}
	
	@RequestMapping("/upload")
    public @ResponseBody Data upload(@RequestParam(value = "file", required=false) MultipartFile file,
    		Boolean scale, Integer size) {
    	Data data = Data.failure("error");
    	
    	if (file == null || file.isEmpty()) {
    		data = Data.failure(errors.get("NOFILE"));
			return data;
		}
    	
    	if (!checkFileType(file.getOriginalFilename())) {
    		data = Data.failure(errors.get("TYPE"));
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
			data = Data.failure(errors.get("UNKNOWN"));
		}
    	
    	return data;
    }
    
	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}
}
