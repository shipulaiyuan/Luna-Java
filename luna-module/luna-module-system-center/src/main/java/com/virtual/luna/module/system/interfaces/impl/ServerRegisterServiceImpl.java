package com.virtual.luna.module.system.interfaces.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.common.base.utils.SnowUtil;
import com.virtual.luna.framework.quartz.core.util.CronUtils;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.infra.system.domain.SysClusters;
import com.virtual.luna.infra.system.domain.SysContainer;
import com.virtual.luna.infra.system.domain.SysJob;
import com.virtual.luna.infra.system.mapper.SysClustersMapper;
import com.virtual.luna.infra.system.mapper.SysContainerMapper;
import com.virtual.luna.framework.web.utils.IpUtils;
import com.virtual.luna.module.system.constant.AddressModeConstants;
import com.virtual.luna.module.system.vo.HealthCheck;
import com.virtual.luna.module.system.vo.RegisterBodyVo;
import com.virtual.luna.module.system.interfaces.IServerRegisterService;
import com.virtual.luna.module.system.quartz.service.JobService;
import com.virtual.luna.module.system.service.ISysContainerService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServerRegisterServiceImpl implements IServerRegisterService {

    private static final Logger logger = LoggerFactory.getLogger(ServerRegisterServiceImpl.class);

    @Autowired
    private SysClustersMapper sysClustersMapper;

    @Autowired
    private SysContainerMapper sysContainerMapper;

    @Autowired
    private ISysContainerService sysContainerService;

    @Autowired
    private JobService jobService;

    @Transactional(rollbackFor = Exception.class)
    public int serverRegister(RegisterBodyVo registerBodyDto, HttpServletRequest request) {
        try {
            logger.info("开始服务注册流程...");

            // 解析注册信息
            HealthCheck healthCheck = registerBodyDto.getHealthCheck();
            String serviceUniqueKey = registerBodyDto.getServiceUniqueKey();
            String serviceUniqueName = registerBodyDto.getServiceUniqueName();

            String addressMode = healthCheck.getAddressMode();
            String address = healthCheck.getAddress();
            String port = healthCheck.getPort();
            long intervalMs = healthCheck.getIntervalMs();
            String endpoint = healthCheck.getEndpoint();
            boolean enabled = healthCheck.isEnabled();

            logger.debug("接收到的注册数据:");
            logger.debug("服务唯一键: {}", serviceUniqueKey);
            logger.debug("服务唯一名称: {}", serviceUniqueName);
            logger.debug("地址模式: {}", addressMode);
            logger.debug("地址: {}", address);
            logger.debug("端口: {}", port);
            logger.debug("健康检查间隔 (ms): {}", intervalMs);
            logger.debug("健康检查接口: {}", endpoint);
            logger.debug("健康检查是否启用: {}", enabled);

            // 根据地址模式获取地址
            if (AddressModeConstants.SERVER.equals(addressMode)) {
                address = IpUtils.getIpAddr(request);
                logger.debug("使用服务器地址: {}", address);
            }

            // 查询集群信息，如果不存在则创建
            SysClusters sysClusters = sysClustersMapper.selectOne(
                    new LambdaQueryWrapper<SysClusters>()
                            .eq(SysClusters::getDelFlag, "0")
                            .eq(SysClusters::getClustersKey, serviceUniqueKey)
            );

            if (sysClusters == null) {
                logger.info("创建服务集群条目，键为: {}", serviceUniqueKey);
                sysClusters = new SysClusters();
                sysClusters.setClustersKey(serviceUniqueKey);
                sysClusters.setClustersAlias(serviceUniqueName);
                sysClusters.setCreateTime(DateUtils.getNowDate());
                sysClustersMapper.insert(sysClusters);
                logger.info("服务集群条目创建成功.");
            } else {
                logger.info("服务集群条目已存在，键为: {}", serviceUniqueKey);
            }

            // 查询容器信息，如果存在则删除旧记录
            SysContainer sysContainer = sysContainerMapper.selectOne(
                    new LambdaQueryWrapper<SysContainer>()
                            .eq(SysContainer::getDelFlag, "0")
                            .eq(SysContainer::getClustersKey, serviceUniqueKey)
                            .eq(SysContainer::getContainerIp, address)
                            .eq(SysContainer::getContainerPort, port)
            );

            if (sysContainer != null) {

                SysJob sysJob = jobService.selectByName(address + ":" + port);

                if(ObjectUtils.isNotEmpty(sysJob)){
                    logger.info("删除现有定时任务: {}", sysJob);
                    jobService.deleteJob(sysJob.getId());
                    logger.info("现有定时任务条目删除成功.");
                }

                logger.info("删除现有容器条目: {}", sysContainer);
                sysContainerService.deleteContainer(sysContainer.getId());
                logger.info("现有容器条目删除成功.");

            }

            // 创建新的容器记录
            logger.info("创建新的容器条目，服务唯一键为: {}", serviceUniqueKey);
            SysContainer sysContainer1 = new SysContainer();
            sysContainer1.setContainerKey(serviceUniqueKey);
            sysContainer1.setCreateTime(DateUtils.getNowDate());
            sysContainer1.setContainerIp(address);
            sysContainer1.setContainerPort(port);
            String key = String.valueOf(SnowUtil.nextId());
            sysContainer1.setContainerKey(key);
            sysContainer1.setContainerAlias(serviceUniqueName + key);

            if (enabled) {
                sysContainer1.setIsOpenHealthCheck("1");
                sysContainer1.setHealthInterface(endpoint);
                sysContainer1.setHealthTime(intervalMs);
            }

            sysContainer1.setContainerState("0");
            sysContainer1.setIsAutoRegister("1");
            sysContainer1.setClustersKey(serviceUniqueKey);
            sysContainerMapper.insert(sysContainer1);

            // 创建定时任务
            if (enabled) {

                String cornExpression = CronUtils.generateCronExpression(intervalMs);
                if(CronUtils.isValid(cornExpression)){
                    SysJob sysJob = new SysJob();

                    sysJob.setName(address + ":" + port);
                    sysJob.setStatus(1);
                    // Task
                    sysJob.setHandlerName("healthCheckTask");
                    sysJob.setHandlerParam(String.valueOf(key));
                    sysJob.setCronExpression(cornExpression);
                    sysJob.setRetryCount(0);
                    sysJob.setRetryInterval(0);
                    sysJob.setCreateTime(DateUtils.getNowDate());
                    jobService.createJob(sysJob);
                }else{
                    throw new LunaException("corn 表达式生成失败");
                }
            }

            logger.info("新的容器条目创建成功: {}", sysContainer1);

            logger.info("服务注册流程完成.");

            return 1;
        } catch (Exception e) {
            logger.error("服务注册过程中发生错误: {}", e.getMessage(), e);
            throw new RuntimeException("服务注册失败: " + e.getMessage(), e);
        }
    }
}
