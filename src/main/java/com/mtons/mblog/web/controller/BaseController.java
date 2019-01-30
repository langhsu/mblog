/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller;

import com.mtons.mblog.base.storage.StorageFactory;
import com.mtons.mblog.base.utils.MD5;
import com.mtons.mblog.base.utils.MailHelper;
import com.mtons.mblog.base.utils.Printer;
import com.mtons.mblog.config.SiteOptions;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.web.formatter.StringEscapeEditor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Controller 基类
 *
 * @author langhsu
 * @since 3.0
 * 
 */
public class BaseController {
	@Autowired
	protected HttpSession session;
	@Autowired
	protected StorageFactory storageFactory;
	@Autowired
	protected SiteOptions siteOptions;
	@Autowired
	private MailHelper mailHelper;
	@Autowired
	private TaskExecutor taskExecutor;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
	}

	/**
	 * 获取登录信息
	 * 
	 * @return
	 */
	protected AccountProfile getProfile(){
		Subject subject = SecurityUtils.getSubject();
		return (AccountProfile) subject.getPrincipal();
	}
	
	protected void putProfile(AccountProfile profile) {
		SecurityUtils.getSubject().getSession(true).setAttribute("profile", profile);
	}

	protected boolean isAuthenticated() {
		return SecurityUtils.getSubject() != null && (SecurityUtils.getSubject().isAuthenticated() || SecurityUtils.getSubject().isRemembered());
	}
	protected UsernamePasswordToken createToken(String username, String password) {
		return new UsernamePasswordToken(username, MD5.md5(password));
	}

	protected Pageable wrapPageable() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		return PageRequest.of(pageNo - 1, pageSize);
	}

	/**
	 * 包装分页对象
	 *
	 * @param pn 页码
	 * @param pn 页码
	 * @return
	 */
	protected Pageable wrapPageable(Integer pn, Integer pageSize) {
		if (pn == null || pn == 0) {
			pn = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 10;
		}
		return PageRequest.of(pn - 1, pageSize);
	}

	protected String getSuffix(String name) {
		int pos = name.lastIndexOf(".");
		return name.substring(pos);
	}

	protected String view(String view) {
		return "/" + siteOptions.getOptions().get("theme") + view;
	}

	protected void sendEmail(String template, String email, String subject, Map<String, Object> context) {
		taskExecutor.execute(() -> {
			mailHelper.sendEmail(template, email, subject, context);
			Printer.debug(email + " send success");
		});
	}
	
}
