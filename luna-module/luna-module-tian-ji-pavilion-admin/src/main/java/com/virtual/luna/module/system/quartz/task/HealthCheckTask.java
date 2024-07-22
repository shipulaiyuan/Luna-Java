package com.virtual.luna.module.system.quartz.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.virtual.luna.common.base.constant.Constants;
import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.framework.quartz.core.handler.JobHandler;
import com.virtual.luna.infra.system.domain.SysContainer;
import com.virtual.luna.infra.system.domain.SysJob;
import com.virtual.luna.infra.system.mapper.SysContainerMapper;
import com.virtual.luna.module.system.quartz.service.JobService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * 健康检查任务
 */
@Component("healthCheckTask")
public class HealthCheckTask implements JobHandler {

    private static final Logger log = LoggerFactory.getLogger(HealthCheckTask.class);

    @Resource
    private SysContainerMapper sysContainerMapper;

    @Resource
    private JobService jobService;

    /**
     * 执行健康检查任务
     *
     * @param param 任务参数
     * @return 执行结果 ("success", "warn", "error")
     * @throws Exception 执行过程中的异常
     */
    @Override
    public String execute(String param) throws Exception {

        log.info("[{}]开始健康检测",param);

        LambdaQueryWrapper<SysContainer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysContainer::getDelFlag, "0");
        lambdaQueryWrapper.eq(SysContainer::getContainerKey, param);
        lambdaQueryWrapper.eq(SysContainer::getIsOpenHealthCheck, "1");
        SysContainer sysContainer = sysContainerMapper.selectOne(lambdaQueryWrapper);

        if (ObjectUtils.isNotEmpty(sysContainer)) {
            Long warningCount = sysContainer.getWarningCount();
            String containerState = sysContainer.getContainerState();

            // 容器状态为死亡
            if ("1".equals(containerState)) {

                // 删除定时任务
                SysJob sysJob = jobService.selectByParam(param);
                jobService.deleteJob(sysJob.getId());

                return "error";
            }

            // 警告次数 >= 10 不进行检测。
            if (warningCount >= 10 && "3".equals(containerState)) {
                // 修改容器状态为死亡
                sysContainer.setContainerState("1");
                sysContainer.setUpdateTime(DateUtils.getNowDate());
                sysContainerMapper.updateById(sysContainer);

                // 删除定时任务
                SysJob sysJob = jobService.selectByParam(param);
                jobService.deleteJob(sysJob.getId());

                return "error";
            }

            String containerPort = sysContainer.getContainerPort();
            String containerIp = sysContainer.getContainerIp();
            String healthInterface = sysContainer.getHealthInterface();
            String healthType = sysContainer.getHealthType();

            String healthCheckUrl = Constants.HTTP + containerIp + ":" + containerPort + healthInterface;

            boolean isSuccess;

            // GET请求
            if ("0".equals(healthType)) {
                isSuccess = performGetRequest(healthCheckUrl);
            }
            // HEAD请求
            else if ("1".equals(healthType)) {
                isSuccess = performHeadRequest(healthCheckUrl);
            } else {
                return "error";
            }

            if (isSuccess) {
                // 检测成功
                sysContainer.setContainerState("0");
                sysContainer.setWarningCount(0L);
            } else {
                // 检测失败
                warningCount++;
                sysContainer.setWarningCount(warningCount);
                sysContainer.setContainerState("3");
            }

            sysContainer.setUpdateTime(DateUtils.getNowDate());
            sysContainerMapper.updateById(sysContainer);

            return isSuccess ? "success" : "warn";
        } else {

            SysJob sysJob = jobService.selectByParam(param);
            jobService.deleteJob(sysJob.getId());

            // 定时任务删除
            return "error";
        }
    }

    /**
     * 执行 GET 请求
     *
     * @param url 请求的 URL
     * @return 请求是否成功
     * @throws IOException          发生 IO 异常时抛出
     * @throws InterruptedException 线程被中断时抛出
     */
    private boolean performGetRequest(String url) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                // 设置超时时间 0.5秒
                .connectTimeout(Duration.ofMillis(500))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            return statusCode == 200;
        } catch (IOException | InterruptedException e) {
            return false;
        }

    }

    /**
     * 执行 HEAD 请求
     *
     * @param url 请求的 URL
     * @return 请求是否成功
     * @throws IOException          发生 IO 异常时抛出
     * @throws InterruptedException 线程被中断时抛出
     */
    private boolean performHeadRequest(String url) {
        HttpClient httpClient = HttpClient.newBuilder()
                // 设置超时时间 0.5秒
                .connectTimeout(Duration.ofMillis(500))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            int statusCode = response.statusCode();
            return statusCode == 200;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }
}
