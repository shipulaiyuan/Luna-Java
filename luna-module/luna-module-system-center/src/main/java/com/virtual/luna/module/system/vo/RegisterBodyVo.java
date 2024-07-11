package com.virtual.luna.module.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "客户端注册-入参")
public class RegisterBodyVo {

    @Schema(description = "服务key", example = "yd-system-center")
    private String serviceUniqueKey;

    @Schema(description = "服务名", example = "翼渡系统中心服务")
    private String serviceUniqueName;

    @Schema(description = "健康检测配置")
    private HealthCheck healthCheck;

    public String getServiceUniqueName() {
        return serviceUniqueName;
    }

    public void setServiceUniqueName(String serviceUniqueName) {
        this.serviceUniqueName = serviceUniqueName;
    }

    public String getServiceUniqueKey() {
        return serviceUniqueKey;
    }

    public void setServiceUniqueKey(String serviceUniqueKey) {
        this.serviceUniqueKey = serviceUniqueKey;
    }

    public HealthCheck getHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(HealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }
}
