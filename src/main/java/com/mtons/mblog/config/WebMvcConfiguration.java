package com.mtons.mblog.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.mtons.mblog.web.interceptor.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author langhsu
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private BaseInterceptor baseInterceptor;
    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;
    @Autowired
    private SiteOptions siteOptions;

    /**
     * Add intercepter
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/dist/**", "/store/**", "/static/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dist/**")
                .addResourceLocations("classpath:/static/dist/");
        registry.addResourceHandler("/theme/*/dist/**")
                .addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/storage/avatars/**")
                .addResourceLocations("file:///" + siteOptions.getLocation() + "/storage/avatars/");
        registry.addResourceHandler("/storage/thumbnails/**")
                .addResourceLocations("file:///" + siteOptions.getLocation() + "/storage/thumbnails/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter);
    }

}
