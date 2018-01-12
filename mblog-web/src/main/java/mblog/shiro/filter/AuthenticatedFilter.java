/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.shiro.filter;

import java.io.IOException;
import java.util.Formatter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import mblog.base.data.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * @version 1.0.0
 */
public class AuthenticatedFilter extends OncePerRequestFilter {
    private static final String JS = "<script type='text/javascript'>var wp=window.parent; if(wp!=null){while(wp.parent&&wp.parent!==wp){wp=wp.parent;}wp.location.href='%1$s';}else{window.location.href='%1$s';}</script>";
    private String loginUrl = "/login.do";

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            chain.doFilter(request, response);
        } else {
            identifyGuest(subject, request, response, chain);
        }
    }

    protected void identifyGuest(Subject subject, ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        redirectLogin(request, response);
    }

	protected void redirectLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.saveRequest(request);
        String path = WebUtils.getContextPath((HttpServletRequest) request);
        String url = loginUrl;
        if (StringUtils.isNotBlank(path) && path.length() > 1) {
        	url = path + url;
		}
        
        if (isAjaxRequest((HttpServletRequest) request)) {
        	response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(JSON.toJSONString(Data.failure("您还没有登录!")));
        } else {
        	response.getWriter().write(new Formatter().format(JS, url).toString());
        }
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
    
	/**
	 * 判断是否为Ajax请求 <功能详细描述>
	 * 
	 * @param request
	 * @return 是true, 否false
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}

}
