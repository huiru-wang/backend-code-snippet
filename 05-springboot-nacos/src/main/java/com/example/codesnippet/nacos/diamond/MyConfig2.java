package com.example.codesnippet.nacos.diamond;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

/**
 * 注解指定 groupId、dataId
 */
@NacosPropertySource(
        groupId = "DEFAULT_GROUP",
        dataId = "my-config-2",
        autoRefreshed = true
)
public class MyConfig2 {

}
