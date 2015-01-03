/**
 * 
 */
package mblog.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mtons.modules.lang.Const;
import mtons.modules.pojos.UserProfile;

import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
public class AdminAccessInterceptor extends HandlerInterceptorAdapter {
	private String loginUrl = "/login";

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();

		UserProfile profile = (UserProfile) session.getAttribute(Const.KEY_LOGIN);
		
		if (profile == null) {
			String to = request.getContextPath() + loginUrl;
			response.sendRedirect(to);
			return false;
		} else {
			Assert.isTrue(Const.ADMIN_ID == profile.getId(), "您的权限不够");
		}
		return true;
	}

}
