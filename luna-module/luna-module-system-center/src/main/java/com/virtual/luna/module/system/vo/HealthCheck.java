package com.virtual.luna.module.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "健康检测-入参")
public class HealthCheck {

    @Schema(description = "健康检查接口路径", example = "/healthy")
    private String endpoint;
    @Schema(description = "健康检查间隔时间（毫秒）", example = "10000")
    private long intervalMs;
    @Schema(description = "地址获取模式: config（手动配置）, client（客户端自动获取）, server（服务端自动获取）", example = "server")
    private String addressMode;
    @Schema(description = "健康检查地址，当 address_mode 为 config 时使用", example = "172.17.0.231")
    private String address;
    @Schema(description = "健康检查端口", example = "8091")
    private String port;
    @Schema(description = "是否启用健康检查", example = "true")
    private boolean enabled;

    public String getAddressMode() {
        return addressMode;
    }

    public void setAddressMode(String addressMode) {
        this.addressMode = addressMode;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public long getIntervalMs() {
        return intervalMs;
    }

    public void setIntervalMs(long intervalMs) {
        this.intervalMs = intervalMs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
