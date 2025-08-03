package com.example.codesnippet.nacos.config;

import com.alibaba.cloud.nacos.annotation.NacosConfigListener;
import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用于处理复杂数据类型的动态配置
 */
@Slf4j
@Component
public class DynamicConfig {

    private CustomConfig customConfig;

    /**
     * initNotify：应用启动时，监听器会立即执行一次，获取当前配置内容
     */
    @NacosConfigListener(
            group = "DEFAULT_GROUP",
            dataId = "springboot-nacos-custom-data-1",
            initNotify = true
    )
    public void customConfig(String data) {
        log.info("customConfig: {}", data);
        this.customConfig = JSON.parseObject(data, CustomConfig.class);
    }

    public CustomConfig getCustomConfig() {
        return customConfig;
    }

    @Data
    public static class CustomConfig {

        private String key;

        private String value;
    }
}
