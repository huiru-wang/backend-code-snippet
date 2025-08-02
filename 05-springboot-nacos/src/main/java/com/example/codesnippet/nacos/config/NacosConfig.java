package com.example.codesnippet.nacos.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosProperty;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 注册Nacos服务
 * <pre>
 *     1. 指定注册中心地址；
 *     2. 指定namespace
 * </pre>
 */
@Configuration
public class NacosConfig {

//    @Bean
//    public ConfigService nacosConfigService() throws NacosException {
//        Properties properties = new Properties();
//        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
//        properties.setProperty(PropertyKeyConst.NAMESPACE, "00079ffa-7e3e-4d31-861e-a807d1cdeaef");
//        return NacosFactory.createConfigService(properties);
//    }
}
