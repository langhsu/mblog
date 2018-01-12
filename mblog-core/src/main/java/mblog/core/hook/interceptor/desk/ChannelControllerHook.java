package mblog.core.hook.interceptor.desk;

import mblog.core.hook.interceptor.InterceptorHookSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GroupVidewController拦截钩子
 *
 * @author Beldon 2015/10/31
 */
@Component
public class ChannelControllerHook extends InterceptorHookSupport {


    @Override
    public void init() {
        this.plugins = getPlugins(ChannelControllerInterceptorListener.class);
    }

    @Override
    public String[] getInterceptor() {
        //说明要拦截的controller
        return new String[]{"mblog.web.controller.desk.ChannelController"};
    }

    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {
        super.onPreHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception {
        super.onPostHandle(request, response, handler, modelAndView);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception {
        super.onAfterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {
        super.onAfterConcurrentHandlingStarted(request, response, handler);
    }

    public interface ChannelControllerInterceptorListener extends InterceptorListener {

    }
}
