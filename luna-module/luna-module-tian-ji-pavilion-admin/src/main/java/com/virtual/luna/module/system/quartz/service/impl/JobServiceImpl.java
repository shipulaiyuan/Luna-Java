package com.virtual.luna.module.system.quartz.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.framework.quartz.core.handler.JobHandler;
import com.virtual.luna.framework.quartz.core.scheduler.SchedulerManager;
import com.virtual.luna.framework.quartz.core.util.CronUtils;
import com.virtual.luna.infra.system.domain.SysJob;
import com.virtual.luna.infra.system.mapper.SysJobMapper;
import com.virtual.luna.module.system.quartz.service.JobService;
import jakarta.annotation.PostConstruct;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private SysJobMapper jobMapper;

    @Autowired
    private SchedulerManager schedulerManager;

    @PostConstruct
    public void init() throws SchedulerException
    {
        schedulerManager.clear();

        LambdaQueryWrapper<SysJob> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysJob::getStatus,"1");
        lambdaQueryWrapper.eq(SysJob::getDelFlag,"0");
        List<SysJob> jobList = jobMapper.selectList(lambdaQueryWrapper);

        for (SysJob job : jobList)
        {
            schedulerManager.addJob(job.getId(), job.getHandlerName(), job.getHandlerParam(), job.getCronExpression(), job.getRetryCount(), job.getRetryInterval());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createJob(SysJob job) throws SchedulerException {

        // 表达式校验
        validateCronExpression(job.getCronExpression());

        // JobHandler 是否存在
        validateJobHandlerExists(job.getHandlerName());

        // 1. 插入 Job
        job.setStatus(0);
        job.setCreateTime(DateUtils.getNowDate());
        jobMapper.insert(job);

        // 2. 添加 Job 到 Quartz 中
        schedulerManager.addJob(job.getId(), job.getHandlerName(), job.getHandlerParam(), job.getCronExpression(),
                job.getRetryCount(), job.getRetryInterval());

        // 3. 更新 Job 状态
        job.setStatus(1);
        job.setUpdateTime(DateUtils.getNowDate());
        jobMapper.updateById(job);

        return job.getId();
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(SysJob job) throws SchedulerException {

        // 表达式校验
        validateCronExpression(job.getCronExpression());

        // JobHandler 是否存在
        validateJobHandlerExists(job.getHandlerName());

        if (!job.getStatus().equals("1")) {
            throw new SchedulerException("只有开启状态的任务，才可以修改");
        }

        // 1. 更新 Job
        job.setUpdateTime(DateUtils.getNowDate());
        jobMapper.updateById(job);

        // 2. 更新 Job 到 Quartz 中
        schedulerManager.updateJob(job.getHandlerName(), job.getHandlerParam(), job.getCronExpression(),
                job.getRetryCount(), job.getRetryInterval());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJobStatus(Long id, Integer status) throws SchedulerException {

        // 1. 获取 Job
        SysJob job = jobMapper.selectById(id);

        // 2. 更新状态
        job.setStatus(status);
        job.setUpdateTime(DateUtils.getNowDate());
        jobMapper.updateById(job);

        // 3. 更新 Quartz 状态
        if (status == 1) { // 正常状态
            schedulerManager.resumeJob(job.getHandlerName());
        } else { // 暂停状态
            schedulerManager.pauseJob(job.getHandlerName());
        }
    }

    @Override
    public void triggerJob(Long id) throws SchedulerException {
        // 1. 获取 Job
        SysJob job = jobMapper.selectById(id);

        // 2. 触发 Quartz 中的 Job
        schedulerManager.triggerJob(job.getId(), job.getHandlerName(), job.getHandlerParam());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncJob() throws SchedulerException {
        // 1. 查询 Job 列表
        List<SysJob> jobList = jobMapper.selectList(null);

        // 2. 遍历处理
        for (SysJob job : jobList) {
            // 2.1 先删除，再创建
            schedulerManager.deleteJob(job.getHandlerName());
            schedulerManager.addJob(job.getId(), job.getHandlerName(), job.getHandlerParam(), job.getCronExpression(),
                    job.getRetryCount(), job.getRetryInterval());

            // 2.2 如果状态为暂停，则需要暂停
            if (Objects.equals(job.getStatus(), 2)) { // 暂停状态
                schedulerManager.pauseJob(job.getHandlerName());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(Long id) throws SchedulerException {
        // 1. 获取 Job
        SysJob job = jobMapper.selectById(id);

        // 2. 删除 Job
        jobMapper.deleteById(id);

        // 3. 删除 Quartz 中的 Job
        schedulerManager.deleteJob(job.getHandlerName());
    }

    @Override
    public SysJob getJob(Long id) {
        return jobMapper.selectById(id);
    }

    @Override
    public List<SysJob> getJobList() {
        return jobMapper.selectList(null);
    }

    @Override
    public List<SysJob> selectByName(String value) {
        LambdaQueryWrapper<SysJob> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysJob::getDelFlag,"0");
        lambdaQueryWrapper.eq(SysJob::getName,value);
        List<SysJob> sysJobs = jobMapper.selectList(lambdaQueryWrapper);
        return sysJobs;
    }


    private void validateCronExpression(String cronExpression) throws SchedulerException {
        if (!CronUtils.isValid(cronExpression)) {
            throw new SchedulerException("cron表达式不正确");
        }
    }

    private void validateJobHandlerExists(String handlerName) throws SchedulerException {
        Object handler = SpringUtil.getBean(handlerName);
        if (handler == null) {
            throw new SchedulerException("定时任务的处理器 Bean 不存在");
        }
        if (!(handler instanceof JobHandler)) {
            throw new SchedulerException("定时任务的处理器 Bean 类型不正确，未实现 JobHandler 接口");
        }
    }

    @Override
    public SysJob selectByParam(String param) {
        LambdaQueryWrapper<SysJob> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysJob::getDelFlag,"0");
        lambdaQueryWrapper.eq(SysJob::getHandlerParam,param);
        SysJob sysJob = jobMapper.selectOne(lambdaQueryWrapper);
        return sysJob;
    }
}

