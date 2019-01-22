package com.mtons.mblog.config;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by langhsu on 2017/11/13.
 */
@Configuration
@EnableCaching
public class EhCacheConfig {

    /**
     * 邮件发送线程池, 如果任务量多可以适当扩大
     * @return
     */
    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(1);
    }

    /**
     * EhCache的配置
     */
    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cacheManager) {
        return new EhCacheCacheManager(cacheManager);
    }

    /**
     * EhCache的配置
     */
    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }

}
