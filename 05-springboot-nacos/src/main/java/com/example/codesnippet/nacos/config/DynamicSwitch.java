package com.example.codesnippet.nacos.config;

import com.alibaba.cloud.nacos.annotation.NacosConfig;
import org.springframework.stereotype.Component;

@Component
public class DynamicSwitch {

    /**
     * 可以用来简单的动态配置、开关、降级开关等等
     * 声明key，只能使用properties、yaml类型的配置
     */
    @NacosConfig(
            group = "DEFAULT_GROUP",
            dataId = "springboot-nacos-custom-data-2",
            key = "downgrade"
    )
    public static boolean downgrade;
}
