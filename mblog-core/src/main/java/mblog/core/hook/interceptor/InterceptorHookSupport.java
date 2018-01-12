package mblog.core.hook.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截类钩子支持类
 *
 * @author Beldon 2015/10/30
 */
public abstract class InterceptorHookSupport implements InterceptorHook {
	@Autowired
	private ApplicationContext applicationContext;
	
    @Autowired
    protected InterceptorHookManager interceptorHookManager;

    protected Map<String, InterceptorListener> plugins;

    /**
     * 初始化，编写Event钩子时，应该编写此方法，方法应有以下内容
     * <p>
     * 如：this.plugins = getPlugins(IndexControllerInterceptorListener.class);
     * <p>
     * 其中IndexControllerInterceptorListener是继承InterceptorListener的接口，拥有区分一类钩子下的所有插件
     */
    @PostConstruct
    public abstract void init();

    @PreDestroy
    public void destroy() {
        interceptorHookManager.removeInterceptorHook(this);
    }

    /**
     * 获取此钩子下的所有插件,调用此方法时会获取所有Event钩子的插件
     *
     * @return
     */
    protected Map<String, InterceptorListener> getPlugins() {
        return getPlugins(InterceptorListener.class);
    }

    /**
     * 获取钩子
     *
     * @param clazz
     * @return
     */
    public Map<String, InterceptorListener> getPlugins(Class clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    protected void onPreHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {
        Iterator<Map.Entry<String, InterceptorListener>> it = this.plugins.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().preHandle(request, response, handler);
        }
    }

    protected void onPostHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception {
        Iterator<Map.Entry<String, InterceptorListener>> it = this.plugins.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().postHandle(request, response, handler, modelAndView);
        }
    }

    protected void onAfterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception {
        Iterator<Map.Entry<String, InterceptorListener>> it = this.plugins.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().afterCompletion(request, response, handler, ex);
        }
    }

    protected void onAfterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {
        Iterator<Map.Entry<String, InterceptorListener>> it = this.plugins.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().afterConcurrentHandlingStarted(request, response, handler);
        }
    }


    /**
     * 插件接口
     */
    public interface InterceptorListener {
        void preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception;

        void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception;

        void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception;

        void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception;
    }
}
