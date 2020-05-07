package com.mtons.mblog.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Maps;
import com.mtons.mblog.modules.template.TemplateDirective;
import com.mtons.mblog.modules.template.method.TimeAgoMethod;
import com.mtons.mblog.shiro.tags.ShiroTags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : langhsu
 * @since 3.0
 */
@Slf4j
@Configuration
@EnableAsync
public class SiteConfiguration {
    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void setSharedVariable() {
        Map<String, Object> vars = Maps.newHashMap();
        Map<String, TemplateDirective> map = applicationContext.getBeansOfType(TemplateDirective.class);

        map.forEach((k, v) -> {
            String name = v.getName();
            if (name.indexOf(".") > 0) {
                String[] names = name.split("\\.");
                Map<String, Object> child = (Map<String, Object>) vars.computeIfAbsent(names[0], key -> new HashMap<>());
                child.put(names[1], v);
            } else {
                vars.put(v.getName(), v);
            }
        });

        try {
            configuration.setSharedVariables(vars);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
//        map.forEach((k, v) -> configuration.setSharedVariable(v.getName(), v));
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("shiro", new ShiroTags());
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("mtons.mblog.logThread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    @Bean
    @ConditionalOnClass({JSON.class})
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

//    @Bean
//    public HttpMessageConverters httpMessageConverters(){
//        FastJsonHttpMessageConverter jsonHttpMessageConverter = fastJsonHttpMessageConverter();
//        return new HttpMessageConverters(jsonHttpMessageConverter);
//    }
}
