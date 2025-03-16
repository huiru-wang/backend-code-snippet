package com.example.codesnippet.nacosdatasource.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.PropertyKeyConst;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class SentinelDataSource implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.put(PropertyKeyConst.NAMESPACE, "0f89cf05-109f-44a8-aa15-217501d6a5cd");

        String GROUP_ID = "Sentinel_Demo";
        String DATA_ID = "com.alibaba.csp.sentinel.demo.flow.rule";
        // 读取时，从nacos中读取
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(properties, GROUP_ID, DATA_ID,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
}
