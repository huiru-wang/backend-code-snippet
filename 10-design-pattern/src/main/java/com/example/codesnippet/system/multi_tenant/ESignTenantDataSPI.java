package com.example.codesnippet.system.multi_tenant;

import java.util.List;

/**
 * 核心SPI，业务方实现这2个远程调用接口
 */
public interface ESignTenantDataSPI {

    /**
     * 获取租户字段占位符
     *
     * @param tenantId 租户id
     */
    Object getTemplatePlaceHolder(String tenantId);

    /**
     * 获取员工档案数据
     *
     * @param tenantId 租户id
     */
    Object getEmployeeRoster(String tenantId, List<String> placeHolders);
}
