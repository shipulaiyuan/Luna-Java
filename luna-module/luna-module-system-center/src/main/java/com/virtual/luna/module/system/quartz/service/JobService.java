package com.virtual.luna.module.system.quartz.service;

import com.virtual.luna.infra.system.domain.SysJob;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 定时任务 Service 接口
 *
 */
public interface JobService {

    /**
     * 创建定时任务
     *
     * @param job 定时任务对象
     * @return 创建的定时任务ID
     * @throws SchedulerException 如果调度器出现异常
     */
    Long createJob(SysJob job) throws SchedulerException;

    /**
     * 更新定时任务
     *
     * @param job 定时任务对象
     * @throws SchedulerException 如果调度器出现异常
     */
    void updateJob(SysJob job) throws SchedulerException;

    /**
     * 更新定时任务状态
     *
     * @param id 定时任务ID
     * @param status 新的状态
     * @throws SchedulerException 如果调度器出现异常
     */
    void updateJobStatus(Long id, Integer status) throws SchedulerException;

    /**
     * 触发定时任务
     *
     * @param id 定时任务ID
     * @throws SchedulerException 如果调度器出现异常
     */
    void triggerJob(Long id) throws SchedulerException;

    /**
     * 同步所有定时任务
     *
     * @throws SchedulerException 如果调度器出现异常
     */
    void syncJob() throws SchedulerException;

    /**
     * 删除定时任务
     *
     * @param id 定时任务ID
     * @throws SchedulerException 如果调度器出现异常
     */
    void deleteJob(Long id) throws SchedulerException;

    /**
     * 获取定时任务
     *
     * @param id 定时任务ID
     * @return 定时任务对象
     */
    SysJob getJob(Long id);

    /**
     * 获取所有定时任务
     *
     * @return 定时任务列表
     */
    List<SysJob> getJobList();

    /**
     * 根据参数查询定时任务
     * @param name
     * @return
     */
    SysJob selectByName(String name);

    /**
     * 根据查询参数查询定时任务
     * @param param
     * @return
     */
    SysJob selectByParam(String param);

}
