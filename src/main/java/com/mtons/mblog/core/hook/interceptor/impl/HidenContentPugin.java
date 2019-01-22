package com.mtons.mblog.core.hook.interceptor.impl;

import com.mtons.mblog.core.hook.interceptor.InterceptorHookSupport;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.service.CommentService;
import com.mtons.mblog.web.controller.site.ChannelController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Beldon
 */
@Component
public class HidenContentPugin extends InterceptorHookSupport {
    @Autowired
    private CommentService commentService;

    private static final String SHOW = "<blockquote>隐藏内容，请回复后查看</blockquote>";
    @Override
    public String[] getInterceptor() {
        //说明要拦截的controller
        return new String[]{ChannelController.class.getName()};
    }

    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception {
        PostVO ret = (PostVO) modelAndView.getModelMap().get("view");
        if (ret != null && check(ret.getId(), ret.getAuthor().getId())) {
            PostVO post = new PostVO();
            BeanUtils.copyProperties(ret, post);
            post.setContent(replace(post.getContent()));
            modelAndView.getModelMap().put("view", post);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception {

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

    }

    private String replace(String content) {
        String c = content.replaceAll("<hide>([\\s\\S]*)</hide>", SHOW);
        c = c.replaceAll("&lt;hide&gt;([\\s\\S]*)&lt;/hide&gt;", SHOW);
        return c;
    }

    private boolean check(long id, long userId) {
        Subject subject = SecurityUtils.getSubject();
        AccountProfile profile = (AccountProfile) subject.getPrincipal();
        if (profile != null) {
            if (profile.getId() == userId) {
                return false;
            }
            List l = commentService.findAllByAuthorIdAndToId(profile.getId(), id);
            if (l.size() > 0) {
                return false;
            }
        }
        return true;
    }
}
