package com.example.codesnippet.system.multi_tenant;

import lombok.Data;

@Data
public class TenantConfig {

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 租户协议
     */
    private String protocol;

    /**
     * HTTP：url
     * RPC：serviceVersion
     */
    private String endpoint;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 重试次数
     */
    private Integer retryCount = 3;
}
