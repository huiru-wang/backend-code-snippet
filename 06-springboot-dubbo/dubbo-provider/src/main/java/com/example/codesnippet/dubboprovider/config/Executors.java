package com.example.codesnippet.dubboprovider.config;

import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration
public class Executors {

    @Bean
    public Executor demoExecutor() {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory("Custom");
        return java.util.concurrent.Executors.newFixedThreadPool(10, namedThreadFactory);
    }
}
