package com.virtual.luna.infra.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("sys_route_match")
public class SysRouteMatch {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long routeId;
    private String matchPath;
    private String matchHosts;
    private String matchMethods;
    private String matchHeaders;
    private String matchQueryParameters;
    private String matchTransforms;
    private Date createTime;
    private Date updateTime;
    private String remark;
    private String delFlag;

    public String getMatchTransforms() {
        return matchTransforms;
    }

    public void setMatchTransforms(String matchTransforms) {
        this.matchTransforms = matchTransforms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getMatchPath() {
        return matchPath;
    }

    public void setMatchPath(String matchPath) {
        this.matchPath = matchPath;
    }

    public String getMatchHosts() {
        return matchHosts;
    }

    public void setMatchHosts(String matchHosts) {
        this.matchHosts = matchHosts;
    }

    public String getMatchMethods() {
        return matchMethods;
    }

    public void setMatchMethods(String matchMethods) {
        this.matchMethods = matchMethods;
    }

    public String getMatchHeaders() {
        return matchHeaders;
    }

    public void setMatchHeaders(String matchHeaders) {
        this.matchHeaders = matchHeaders;
    }

    public String getMatchQueryParameters() {
        return matchQueryParameters;
    }

    public void setMatchQueryParameters(String matchQueryParameters) {
        this.matchQueryParameters = matchQueryParameters;
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
