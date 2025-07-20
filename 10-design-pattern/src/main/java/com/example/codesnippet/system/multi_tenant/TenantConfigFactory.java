package com.example.codesnippet.system.multi_tenant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TenantConfigFactory {

    private static final Map<String, TenantConfig> tenantConfigMap = new ConcurrentHashMap<>();

    // 配置注入
    public TenantConfigFactory(List<TenantConfig> tenantConfigs) {
        Optional.ofNullable(tenantConfigs)
                .orElse(new ArrayList<>())
                .forEach(item -> {
                    tenantConfigMap.put(item.getTenantId(), item);
                });
    }

    // 工厂方法
    public static TenantConfig getTenantConfig(String tenantId) {
        return tenantConfigMap.get(tenantId);
    }
}
