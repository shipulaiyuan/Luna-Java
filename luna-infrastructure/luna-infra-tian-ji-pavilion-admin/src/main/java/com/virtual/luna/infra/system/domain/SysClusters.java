package com.virtual.luna.infra.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("sys_clusters")
public class SysClusters {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String clustersAlias;

    private String clustersKey;

    private String loadBalancingPolicy;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private String delFlag;

    private String isBlacklist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClustersAlias() {
        return clustersAlias;
    }

    public void setClustersAlias(String clustersAlias) {
        this.clustersAlias = clustersAlias;
    }

    public String getClustersKey() {
        return clustersKey;
    }

    public void setClustersKey(String clustersKey) {
        this.clustersKey = clustersKey;
    }

    public String getLoadBalancingPolicy() {
        return loadBalancingPolicy;
    }

    public void setLoadBalancingPolicy(String loadBalancingPolicy) {
        this.loadBalancingPolicy = loadBalancingPolicy;
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

    public String getIsBlacklist() {
        return isBlacklist;
    }

    public void setIsBlacklist(String isBlacklist) {
        this.isBlacklist = isBlacklist;
    }
}
