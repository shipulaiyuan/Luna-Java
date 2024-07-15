package com.virtual.luna.framework.web.domin;

import com.virtual.luna.common.base.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description="Entity基础参数")
public class BaseTimeEntity extends BaseEntity {

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-07-05 12:34:56")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-07-05 12:34:56")
    private Date updateTime;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "This is a remark.")
    private String remark;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Schema(description = "删除标志（0代表存在 2代表删除）", example = "0")
    private String delFlag;

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
