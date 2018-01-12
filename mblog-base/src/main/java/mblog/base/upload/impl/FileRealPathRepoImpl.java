/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.upload.impl;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

/**
 * @author langhsu
 *
 */
@Service("fileRealPathRepo")
public class FileRealPathRepoImpl extends AbstractFileRepo implements ServletContextAware {
	private static String KEY = "relative";

	private ServletContext context;

	@PostConstruct
	public void init() {
		fileRepoFactory.addRepo(KEY, this);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}
	
	@Override
	public String getRoot() {
		return context.getRealPath("/");
	}
}
