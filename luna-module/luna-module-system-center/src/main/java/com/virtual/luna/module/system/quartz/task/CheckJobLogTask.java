package com.virtual.luna.module.system.quartz.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.virtual.luna.framework.quartz.core.handler.JobHandler;
import com.virtual.luna.infra.system.domain.SysJobLog;
import com.virtual.luna.infra.system.mapper.SysJobLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component("checkJobLogTask")
public class CheckJobLogTask implements JobHandler {

    @Resource
    private SysJobLogMapper sysJobLogMapper;

    @Override
    public String execute(String param) throws Exception {

        LambdaQueryWrapper<SysJobLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysJobLog::getHandlerName,"healthCheckTask");
        lambdaQueryWrapper.ne(SysJobLog::getResult,"success");
        int delete = sysJobLogMapper.delete(lambdaQueryWrapper);
        return "deleteSuccess";
    }
}
