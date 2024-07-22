package com.virtual.luna.module.system.quartz.service.impl;

import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.framework.quartz.core.enums.JobLogStatusEnum;
import com.virtual.luna.infra.system.domain.SysJobLog;
import com.virtual.luna.infra.system.mapper.SysJobLogMapper;
import com.virtual.luna.module.system.quartz.service.JobLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static net.minidev.asm.ConvertDate.convertToDate;

/**
 * Job 日志 Service 实现类
 */
@Service
public class JobLogServiceImpl implements JobLogService {

    private static final Logger log = LoggerFactory.getLogger(JobLogServiceImpl.class);

    @Autowired
    private SysJobLogMapper jobLogMapper;

    @Override
    public Long createJobLog(Long jobId, LocalDateTime beginTime, String jobHandlerName, String jobHandlerParam, Integer executeIndex) {
        SysJobLog log = new SysJobLog();
        log.setJobId(jobId);
        log.setHandlerName(jobHandlerName);
        log.setHandlerParam(jobHandlerParam);
        log.setExecuteIndex(executeIndex);
        log.setBeginTime(DateUtils.convertToDate(beginTime));
        log.setStatus(JobLogStatusEnum.RUNNING.getStatus());
        jobLogMapper.insert(log);
        return log.getId();
    }

    @Override
    public void updateJobLogResultAsync(Long logId, LocalDateTime endTime, Integer duration, boolean success, String result) {
        try {
            SysJobLog updateObj = new SysJobLog();
            updateObj.setId(logId);
            updateObj.setEndTime(DateUtils.convertToDate(endTime));
            updateObj.setDuration(duration);
            updateObj.setStatus(success ? JobLogStatusEnum.SUCCESS.getStatus() : JobLogStatusEnum.FAILURE.getStatus());
            updateObj.setResult(result);
            jobLogMapper.updateById(updateObj);
        } catch (Exception ex) {
            log.error(
                    "[updateJobLogResultAsync][logId({}) endTime({}) duration({}) success({}) result({})]", logId, endTime, duration, success, result, ex
            );
        }
    }
}
