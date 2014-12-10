/**
 * 
 */
package hilo.web.controller.blog;

import hilo.core.context.AppContext;
import hilo.web.controller.BaseController;

import java.io.IOException;

import mtons.commons.pojos.Data;

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
    public @ResponseBody Data upload(@RequestParam(value = "file", required=false) MultipartFile file) {
    	Data data = Data.failure("error");
    	
    	if (file.isEmpty()) {
			return data;
		}
    	
    	try {
			String path = copyFile("/", appContext.getTempDir(), file);
			data = Data.success("", path);
		} catch (IOException e) {
			//FIXME: error handle
		}
    	
    	return data;
    }
    
}
