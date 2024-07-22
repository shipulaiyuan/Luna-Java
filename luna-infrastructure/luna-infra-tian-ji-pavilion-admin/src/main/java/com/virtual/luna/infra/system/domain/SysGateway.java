package com.virtual.luna.infra.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("sys_gateway")
public class SysGateway {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String gatewayAlias;
    private String gatewayLink;
    private String gatewayKey;
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

    public String getGatewayAlias() {
        return gatewayAlias;
    }

    public void setGatewayAlias(String gatewayAlias) {
        this.gatewayAlias = gatewayAlias;
    }

    public String getGatewayLink() {
        return gatewayLink;
    }

    public void setGatewayLink(String gatewayLink) {
        this.gatewayLink = gatewayLink;
    }

    public String getGatewayKey() {
        return gatewayKey;
    }

    public void setGatewayKey(String gatewayKey) {
        this.gatewayKey = gatewayKey;
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

