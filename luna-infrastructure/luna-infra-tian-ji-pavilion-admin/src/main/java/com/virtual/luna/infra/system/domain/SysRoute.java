package com.virtual.luna.infra.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("sys_route")
public class SysRoute {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String gatewayKey;
    private String clustersKey;
    private String routeAlias;
    private String routeKey;
    private Long routeOrder;
    private Long maxRequestBodySize;
    private String authorizationPolicy;
    private String corsPolicy;
    private String metaData;
    private String rateLimiterPolicy;
    private String outputCachePolicy;
    private String timeoutPolicy;
    private String timeout;
    private String otherConfig;
    private Date createTime;
    private Date updateTime;
    private String remark;
    private String delFlag;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGatewayKey() {
        return gatewayKey;
    }

    public void setGatewayKey(String gatewayKey) {
        this.gatewayKey = gatewayKey;
    }

    public String getClustersKey() {
        return clustersKey;
    }

    public void setClustersKey(String clustersKey) {
        this.clustersKey = clustersKey;
    }

    public String getRouteAlias() {
        return routeAlias;
    }

    public void setRouteAlias(String routeAlias) {
        this.routeAlias = routeAlias;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public Long getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(Long routeOrder) {
        this.routeOrder = routeOrder;
    }

    public Long getMaxRequestBodySize() {
        return maxRequestBodySize;
    }

    public void setMaxRequestBodySize(Long maxRequestBodySize) {
        this.maxRequestBodySize = maxRequestBodySize;
    }

    public String getAuthorizationPolicy() {
        return authorizationPolicy;
    }

    public void setAuthorizationPolicy(String authorizationPolicy) {
        this.authorizationPolicy = authorizationPolicy;
    }

    public String getCorsPolicy() {
        return corsPolicy;
    }

    public void setCorsPolicy(String corsPolicy) {
        this.corsPolicy = corsPolicy;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getRateLimiterPolicy() {
        return rateLimiterPolicy;
    }

    public void setRateLimiterPolicy(String rateLimiterPolicy) {
        this.rateLimiterPolicy = rateLimiterPolicy;
    }

    public String getOutputCachePolicy() {
        return outputCachePolicy;
    }

    public void setOutputCachePolicy(String outputCachePolicy) {
        this.outputCachePolicy = outputCachePolicy;
    }

    public String getTimeoutPolicy() {
        return timeoutPolicy;
    }

    public void setTimeoutPolicy(String timeoutPolicy) {
        this.timeoutPolicy = timeoutPolicy;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getOtherConfig() {
        return otherConfig;
    }

    public void setOtherConfig(String otherConfig) {
        this.otherConfig = otherConfig;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}

