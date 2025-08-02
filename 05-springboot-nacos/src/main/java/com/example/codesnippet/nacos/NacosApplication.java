package com.example.codesnippet.nacos;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1. 注解注册Nacos
 * 2. 手动配置ConfigService注册Nacos：{@link com.example.codesnippet.nacos.config.NacosConfig}
 */
@SpringBootApplication
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "localhost:8848"))
public class NacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class, args);
    }
}
