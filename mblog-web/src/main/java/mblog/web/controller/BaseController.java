/**
 * 
 */
package mblog.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import mtons.commons.lang.Const;
import mtons.commons.pojos.Paging;
import mtons.commons.pojos.UserProfile;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

/**
 * @author langhsu
 *
 */
public class BaseController{
	@Autowired
	protected HttpSession session;
	
	/**
	 * 获取登录信息
	 * 
	 * @return
	 */
	protected UserProfile getProfile() {
		return (UserProfile) session.getAttribute(Const.KEY_LOGIN);
	}
	
	/**
	 * 设置登录信息
	 * 
	 * @param user
	 */
	protected void putProfile(UserProfile user) {
		session.setAttribute(Const.KEY_LOGIN, user);
	}
	
	/**
	 * 包装分页对象
	 * 
	 * @param pageNo
	 * @return
	 */
	protected Paging wrapPaging(Integer pageNo) {
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		return new Paging(pageNo, Paging.DEFAULT_RESULTS);
	}
	
	/**
	 * 复制文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	protected String copyFile(String root, String dir, MultipartFile file) throws IOException {
		String realpath = session.getServletContext().getRealPath(root) + dir;
		Date current = new Date();
		String path = DateFormatUtils.format(current, "/yyyyMMddHms") + getSuffix(file.getOriginalFilename());
		File destFile = new File(realpath + path);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		file.transferTo(destFile);
		return dir + path;
	}
	
	protected String getRealPath(String root) {
		return session.getServletContext().getRealPath(root);
	}
	
	protected String getSuffix(String name) {
		int pos = name.lastIndexOf(".");
		return name.substring(pos);
	}

	public String toJson(Object obj) {
		return new Gson().toJson(obj);
	}
	
}
