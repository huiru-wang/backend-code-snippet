package com.example.codesnippet.nacosdatasource.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
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

    @Bean
    public ConfigService nacosConfigService() throws NacosException {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "0f89cf05-109f-44a8-aa15-217501d6a5cd");
        return NacosFactory.createConfigService(properties);
    }
}
