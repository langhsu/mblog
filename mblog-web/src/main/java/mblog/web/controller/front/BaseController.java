/**
 * 
 */
package mblog.web.controller.front;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import mtons.modules.lang.Const;
import mtons.modules.pojos.Page;
import mtons.modules.pojos.UserProfile;

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
	 * @param pn 页码
	 * @return
	 */
	protected Page wrapPage(Integer pn) {
		if (pn == null || pn == 0) {
			pn = 1;
		}
		return new Page(pn, 10);
	}
	
	/**
	 * 复制文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	protected String copyFile(String root, String dir, MultipartFile file) throws IOException {
		String realpath = getRealPath(root) + dir;
		Date current = new Date();
		String path = "/" + current.getTime() + getSuffix(file.getOriginalFilename());
		File destFile = new File(realpath + path);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		file.transferTo(destFile);
		return dir + path;
	}
	
	
	
	protected String genFileName(MultipartFile file) {
		return UUID.randomUUID().toString() + getSuffix(file.getOriginalFilename());
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
	
	protected String getView(String view) {
		return "/default" + view;
	}
}
