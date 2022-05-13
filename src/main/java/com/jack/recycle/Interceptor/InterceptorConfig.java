package com.jack.recycle.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Component
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Resource
    private UserInterceptor userInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/static/**");  // 静态资源过滤
        super.addInterceptors(registry);
    }
}
