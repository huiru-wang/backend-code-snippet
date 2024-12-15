package com.example.codesnippet.config;

import com.example.codesnippet.interceptor.TraceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 顺序：
 * <pre>
 * 请求到达 --> Filter --> Servlet --> Interceptor --> Controller --> Interceptor --> Filter --> 返回
 * </pre>
 * Filter：基于回调，根据配置的多个Filter遍历执行(@WebFilter, @ServletComponentScan)<br/>
 *
 * Interceptor：基于AOP，针对Controller设置前后通知(需要实现HandlerInterceptor ，配置InterceptorConfig)<br/>
 * <p>
 *
 * 注意：@WebFilter的Bean会和Interceptor的配置冲突，使得Interceptor注册不生效,使用：FilterRegistrationBean
 */
@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor()).addPathPatterns("/**");
    }
}
