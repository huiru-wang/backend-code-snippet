package com.example.codesnippet.nacos.diamond;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * 在Spring初始化时，将动态配置的监听，注册到Nacos
 * <pre>
 *     1. 指定group、data-id
 *     2. 一般在启动时，先获取一次最新配置，后续的更新由回调处理
 *     3. 注册回调，在回调中接受消息并处理，一般是转换为此配置的一个数据结构
 *     4. 对外提供配置获取的方法，如get
 * </pre>
 */
@Slf4j
@Component
public class MyConfig1 implements InitializingBean {

    @Resource
    private ConfigService configService;

    private static final String DATA_ID = "my-config-1";

    private static final String GROUP = "DEFAULT_GROUP";

    // 存储配置
    private Map<String, String> CUSTOM_CONFIG_MAP = new HashMap<>();

    /**
     * 动态配置回调监听
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 项目启动时，拉取一次最新配置
        String config = configService.getConfig(DATA_ID, GROUP, 5000);
        CUSTOM_CONFIG_MAP = JSON.parseObject(config, new TypeReference<Map<String, String>>(){});

        // 注册监听
        configService.getConfigAndSignListener(DATA_ID, GROUP, 3000L, new Listener() {
            @Override
            public Executor getExecutor() {
                //如果回调逻辑比较耗时，建议自定义线程池，以免堵塞推送回调线程
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("custom config receive message: {}", configInfo);
                // 处理动态更新的配置回调
                CUSTOM_CONFIG_MAP = JSON.parseObject(configInfo, new TypeReference<Map<String, String>>(){});
            }
        });
    }

    /**
     * 对外提供配置获取的方法
     */
    public String get(String key) {
        return CUSTOM_CONFIG_MAP.get(key);
    }

}
