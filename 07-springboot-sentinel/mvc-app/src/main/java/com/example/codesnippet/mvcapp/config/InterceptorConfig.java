package com.example.codesnippet.mvcapp.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import com.example.codesnippet.mvcapp.exception.CustomBlockExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();
        // 添加自定义BlockExceptionHandler处理器
        config.setBlockExceptionHandler(new CustomBlockExceptionHandler());
        config.setHttpMethodSpecify(true);
        config.setOriginParser(request -> request.getHeader("S-user"));
        // SentinelWebInterceptor 拦截所有接口（"/**"）
        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**").excludePathPatterns("/log/**");
    }
}
