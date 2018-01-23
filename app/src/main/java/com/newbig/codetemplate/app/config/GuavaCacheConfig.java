package com.newbig.codetemplate.app.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class GuavaCacheConfig {
    @Bean
    public CacheManager cacheManger() {
        String[] cacheNames = new String[]{"app"};
        GuavaCacheManager cacheManager = new GuavaCacheManager(cacheNames);
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(1, TimeUnit.DAYS);
        cacheManager.setCacheBuilder(cacheBuilder);
        return cacheManager;
    }
}

