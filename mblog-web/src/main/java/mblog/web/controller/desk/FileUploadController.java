/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.web.controller.desk;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mblog.base.context.Global;
import mblog.base.data.UMEditorResult;
import mblog.web.controller.BaseController;

/**
 * Ueditor 文件上传
 *
 * @author langhsu
 *
 */
@Controller
public class FileUploadController extends BaseController {
	private static HashMap<String, String> errorInfo = new HashMap<>();
	// 文件允许格式
	private static String[] allowFiles = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

	static {
		try{
			errorInfo.put("SUCCESS", "SUCCESS"); //默认成功
			errorInfo.put("NOFILE", URLEncoder.encode("未包含文件上传域","UTF-8"));
			errorInfo.put("TYPE", URLEncoder.encode("不允许的文件格式","UTF-8"));
			errorInfo.put("SIZE", URLEncoder.encode("文件大小超出限制，最大支持2Mb","UTF-8"));
			errorInfo.put("ENTYPE", URLEncoder.encode("请求类型ENTYPE错误","UTF-8"));
			errorInfo.put("REQUEST", URLEncoder.encode("上传请求异常","UTF-8"));
			errorInfo.put("IO", URLEncoder.encode("IO异常","UTF-8"));
			errorInfo.put("DIR", URLEncoder.encode("目录创建失败","UTF-8"));
			errorInfo.put("UNKNOWN", URLEncoder.encode("未知错误","UTF-8"));
		}catch(Exception e){

		}
	}

	@RequestMapping("/aj_um_upload")
	public void upload(@RequestParam(value = "upfile", required=false) MultipartFile file,
					   HttpServletResponse response) throws IOException {
		UMEditorResult data = new UMEditorResult();

		// 保存图片
		if (file != null && !file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			if(file.getSize()>Long.parseLong(Global.getConfig("umeditor.fileSizeLimit"))*1024*1024){
				data.setState(errorInfo.get("SIZE"));
			}
			else{
				if (this.checkFileType(fileName)) {
					try {
						String path = fileRepoFactory.select().storeScale(file, appContext.getThumbsDir(), 600);
						data.setName(fileName);
						data.setOriginalName(fileName);
						data.setType(getSuffix(fileName));
						data.setState(errorInfo.get("SUCCESS"));
						data.setUrl(path);
						data.setSize(file.getSize());
					} catch (Exception e) {
						data.setState(errorInfo.get("UNKNOWN"));
						e.printStackTrace();
					}
				} else {
					data.setState(errorInfo.get("TYPE"));
				}
			}

		} else {
			data.setState(errorInfo.get("NOFILE"));
		}

		response.getWriter().write(JSON.toJSONString(data));
	}

	/**
	 * 文件类型判断
	 *
	 * @param fileName
	 * @return
	 */
	private boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

}
