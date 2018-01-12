package mblog.core.hook.interceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Beldon 2015/10/30
 */
@Component
public class InterceptorHookManager {
	@Autowired
	private ApplicationContext applicationContext;
	
    private Map<String, Set<InterceptorHook>> map = new HashMap<>();

    /**
     * 获取拦截类的所有钩子
     */
    @PostConstruct
    private void init() {
        Map<String, InterceptorHook> map = applicationContext.getBeansOfType(InterceptorHook.class);
        Iterator<Map.Entry<String, InterceptorHook>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, InterceptorHook> entry = it.next();
            InterceptorHook interceptorHook = entry.getValue();
            String[] names = interceptorHook.getInterceptor();
            addInterceptorHook(names, interceptorHook);
        }
    }

    /**
     * 添加钩子到map
     *
     * @param names
     * @param interceptorHook
     */
    private void addInterceptorHook(String[] names, InterceptorHook interceptorHook) {
        if (names != null) {
            for (String name : names) {
                if (!map.containsKey(name)) {
                    Set<InterceptorHook> list = new HashSet<>();
                    list.add(interceptorHook);
                    map.put(name, list);
                } else {
                    map.get(name).add(interceptorHook);
                }
            }
        }
    }

    /**
     * 删除一个Event钩子
     *
     * @param hook 钩子
     */
    public void removeInterceptorHook(InterceptorHook hook) {
        Iterator<Map.Entry<String, Set<InterceptorHook>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Set<InterceptorHook>> entry = it.next();
            entry.getValue().remove(hook);
        }
    }

    public Set<InterceptorHook> getInterceptorHook(HandlerMethod handlerMethod) {
        Set<InterceptorHook> interceptorHooks = new HashSet<>();
        String clazz = handlerMethod.getBean().getClass().getName();
        String method = handlerMethod.getMethod().getName();
        Set<InterceptorHook> c = map.get(clazz);
        Set<InterceptorHook> m = map.get(clazz + "#" + method);
        if (m != null) {
            interceptorHooks.addAll(m);
        }
        if (c != null) {
            interceptorHooks.addAll(c);
        }
        return interceptorHooks;
    }

    public void preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Set<InterceptorHook> interceptorHookSet = getInterceptorHook(handlerMethod);
            for (InterceptorHook interceptorHook : interceptorHookSet) {
                interceptorHook.preHandle(request, response, handlerMethod);
            }
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Set<InterceptorHook> interceptorHookSet = getInterceptorHook(handlerMethod);
            for (InterceptorHook interceptorHook : interceptorHookSet) {
                interceptorHook.postHandle(request, response, handlerMethod, modelAndView);
            }
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Set<InterceptorHook> interceptorHookSet = getInterceptorHook(handlerMethod);
            for (InterceptorHook interceptorHook : interceptorHookSet) {
                interceptorHook.afterCompletion(request, response, handlerMethod, ex);
            }
        }
    }

    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Set<InterceptorHook> interceptorHookSet = getInterceptorHook(handlerMethod);
            for (InterceptorHook interceptorHook : interceptorHookSet) {
                interceptorHook.afterConcurrentHandlingStarted(request, response, handlerMethod);
            }
        }
    }

}
