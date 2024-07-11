package com.virtual.luna.infra.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("sys_container")
public class SysContainer {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String containerAlias;

    private String containerIp;

    private String containerPort;

    private String isOpenHealthCheck;

    private String healthInterface;

    private String healthType;

    private Long healthTime;

    private String containerState;

    private Long warningCount;

    private String isAutoRegister;

    private String clustersKey;

    private String containerKey;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private String delFlag;

    private String isBlacklist;

    public Long getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(Long warningCount) {
        this.warningCount = warningCount;
    }

    public String getIsBlacklist() {
        return isBlacklist;
    }

    public void setIsBlacklist(String isBlacklist) {
        this.isBlacklist = isBlacklist;
    }

    public String getHealthType() {
        return healthType;
    }

    public void setHealthType(String healthType) {
        this.healthType = healthType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContainerAlias() {
        return containerAlias;
    }

    public void setContainerAlias(String containerAlias) {
        this.containerAlias = containerAlias;
    }

    public String getContainerIp() {
        return containerIp;
    }

    public void setContainerIp(String containerIp) {
        this.containerIp = containerIp;
    }

    public String getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(String containerPort) {
        this.containerPort = containerPort;
    }

    public String getIsOpenHealthCheck() {
        return isOpenHealthCheck;
    }

    public void setIsOpenHealthCheck(String isOpenHealthCheck) {
        this.isOpenHealthCheck = isOpenHealthCheck;
    }

    public String getHealthInterface() {
        return healthInterface;
    }

    public void setHealthInterface(String healthInterface) {
        this.healthInterface = healthInterface;
    }

    public Long getHealthTime() {
        return healthTime;
    }

    public void setHealthTime(Long healthTime) {
        this.healthTime = healthTime;
    }

    public String getContainerState() {
        return containerState;
    }

    public void setContainerState(String containerState) {
        this.containerState = containerState;
    }

    public String getIsAutoRegister() {
        return isAutoRegister;
    }

    public void setIsAutoRegister(String isAutoRegister) {
        this.isAutoRegister = isAutoRegister;
    }

    public String getClustersKey() {
        return clustersKey;
    }

    public void setClustersKey(String clustersKey) {
        this.clustersKey = clustersKey;
    }

    public String getContainerKey() {
        return containerKey;
    }

    public void setContainerKey(String containerKey) {
        this.containerKey = containerKey;
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
