/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.context;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @author langhsu
 * 
 */
@Component
public class AppContext implements ServletContextAware {
	
	/*
	 * 文件存储-根目录
	 */
	@Value("${site.store.root}")
	String root = "/data/mblog";
	
	/*
	 * 文件存储-原文件目录
	 */
	String origDir = "/store/orig";
	
	/*
	 * 文件存储-压缩目录
	 */
	String thumbsDir = "/store/thumbs";

	/*
	 * 文件存储-头像目录
	 */
	String avaDir = "/store/ava";
	
	/*
	 * 文件存储-临时文件目录
	 */
	String tempDir = "/store/temp";

	/*
	 * 系统配置信息
	 * - 在 StartupListener 类中加载
	 */
	public Map<String, String> config;

	/**
	 * 容器全局变量
	 */
	private ServletContext servletContext;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getOrigDir() {
		return origDir;
	}

	public void setOrigDir(String origDir) {
		this.origDir = origDir;
	}

	public String getThumbsDir() {
		return thumbsDir;
	}

	public void setThumbsDir(String thumbsDir) {
		this.thumbsDir = thumbsDir;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public String getAvaDir() {
		return avaDir;
	}

	public void setAvaDir(String avaDir) {
		this.avaDir = avaDir;
	}

	public Map<String, String> getConfig() {
		return config;
	}

	public void setConfig(Map<String, String> config) {
		this.config = config;
		//同步更新容器全局变量
		Iterator<Map.Entry<String, String>> iter = config.entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry<String, String> e = iter.next();
			servletContext.setAttribute(e.getKey(), e.getValue());
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
}
