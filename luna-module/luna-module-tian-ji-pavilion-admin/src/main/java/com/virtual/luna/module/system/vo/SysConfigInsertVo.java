package com.virtual.luna.module.system.vo;

import com.virtual.luna.framework.web.domin.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "系统配置新增-入参")
public class SysConfigInsertVo extends PageParam {

    @Schema(description = "自增id")
    private Long id;

    @Schema(description = "配置别名", example = "翼渡系统中心配置文件")
    private String configAlias;

    @Schema(description = "0-未发布 1-已发布", example = "1")
    private String configTag;

    @Schema(description = "配置标签 配置唯一Key", example = "yd-system-center")
    private String configLabel;

    @Schema(description = "配置内容", example = "secretKey: 123")
    private String configInfo;

    @Schema(description = "配置版本", example = "4762523888781312")
    private String configVersion;

    @Schema(description = "备注", example = "备注")
    private String remark;

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigAlias() {
        return configAlias;
    }

    public void setConfigAlias(String configAlias) {
        this.configAlias = configAlias;
    }

    public String getConfigTag() {
        return configTag;
    }

    public void setConfigTag(String configTag) {
        this.configTag = configTag;
    }

    public String getConfigLabel() {
        return configLabel;
    }

    public void setConfigLabel(String configLabel) {
        this.configLabel = configLabel;
    }

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
