package com.virtual.luna.framework.quartz.core.enums;

/**
 * 任务日志的状态枚举
 */
public enum JobLogStatusEnum {

    RUNNING(0), // 运行中
    SUCCESS(1), // 成功
    FAILURE(2); // 失败

    JobLogStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    private final Integer status;

}
