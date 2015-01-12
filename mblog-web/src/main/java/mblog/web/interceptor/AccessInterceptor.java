/**
 * 
 */
package mblog.web.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mtons.modules.lang.Const;
import mtons.modules.pojos.Data;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

/**
 * 
 * 登录拦截器
 * 
 * - 访问地址 不是 loginUrl 的将会判断 session(KEY_LOGIN), 将会重定向到 loginUrl 自动将 refer 追加到
 * url 后面
 * 
 * @author langhsu
 * 
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {
	private String loginUrl = "/login";
	private String[] excludes = { "/logout" };

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();

		String visit = request.getRequestURL().toString();
		
		if (!request.getRequestURI().contains(loginUrl)) {
			Object bean = session.getAttribute(Const.KEY_LOGIN);
			if (bean == null) {
				for (String k : excludes) {
					if (visit.contains(k)) {
						visit = null;
					}
				}
				String to = request.getContextPath() + loginUrl;
				
				if (visit != null && !"post".equalsIgnoreCase(request.getMethod())) {
					String refer = visit;
					if (StringUtils.isNotEmpty(request.getQueryString())) {
						refer += "?" + request.getQueryString();
					}
					to += "?refer=" + URLEncoder.encode(refer, "UTF-8");
				}
				
				if (isAjax(handler)) {
					response.setContentType("application/json;charset=UTF-8");
					Gson gson = new Gson();
					response.getWriter().print(gson.toJson(Data.failure("您还没有登录!")));
				} else {
					response.sendRedirect(to);
				}
				return false;
			}
		}
		return true;
	}
	
	private boolean isAjax(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ResponseBody.class);  
		return responseBodyAnn != null;
	}

}
