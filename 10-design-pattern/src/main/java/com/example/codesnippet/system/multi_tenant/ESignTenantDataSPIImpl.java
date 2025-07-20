package com.example.codesnippet.system.multi_tenant;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 屏蔽外部实现，内部服务都调用此实现，根据租户执行不同的调用逻辑
 */
public class ESignTenantDataSPIImpl implements ESignTenantDataSPI {

    private final Map<String, GenericService> genericService = new ConcurrentHashMap<>();

    public ESignTenantDataSPIImpl() {
        // 启动时根据动态配置，注入RPC相关租户依赖
    }

    @Override
    public Object getTemplatePlaceHolder(String tenantId) {

        // 获取租户配置
        TenantConfig tenantConfig = TenantConfigFactory.getTenantConfig(tenantId);

        // 执行接口调用
        return invoke(tenantId, tenantConfig);
    }

    @Override
    public Object getEmployeeRoster(String tenantId, List<String> placeHolders) {
        // 获取租户配置
        TenantConfig tenantConfig = TenantConfigFactory.getTenantConfig(tenantId);

        // 执行接口调用
        return invoke(tenantId, tenantConfig);
    }

    private Object invoke(String tenantId, TenantConfig tenantConfig) {
        // 执行接口调用
        String protocol = tenantConfig.getProtocol();
        return switch (protocol) {
            case "http" -> HttpUtils.post(tenantConfig.getEndpoint());
            case "rpc" -> genericService.get(tenantId).invoke();
            default -> throw new RuntimeException("unknown protocol: " + protocol);
        };
    }
}
