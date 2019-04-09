package com.mtons.mblog.modules.hook.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器类钩子
 *
 * @author Beldon 2015/10/30
 */
public interface InterceptorHook {

    /**
     * 获取拦截名,可以同时获取多个拦截， 如 return new String[]{"mblog.web.controller.impl.group.GroupVidewController"};
     * <p>
     * 也可以拦截Controller里面的方法，格式如：return new String[]{"mblog.web.controller.impl.group.GroupVidewController#view"};
     *
     * @return
     */
    String[] getInterceptor();

    void preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception;

    void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception;

    void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception;

    void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception;
}
