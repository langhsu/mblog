package hilo.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mtons.commons.lang.Const;
import mtons.commons.pojos.UserContextHolder;
import mtons.commons.pojos.UserProfile;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * 基础拦截器 - 向 request 中添加一些基础变量
 * 
 * @author langhsu
 * 
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		UserProfile bean = (UserProfile) session.getAttribute(Const.KEY_LOGIN);
		if (bean != null) {
			UserContextHolder.setUserProfile(bean);
		}
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		request.setAttribute("base", request.getContextPath());
		request.setAttribute("profile", session.getAttribute(Const.KEY_LOGIN));
	}

}
