package com.virtual.luna.infra.register.core;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtual.luna.framework.web.utils.IpUtils;
import com.virtual.luna.infra.register.constant.AddressModeConstants;
import com.virtual.luna.infra.register.properties.HealthCheck;
import com.virtual.luna.infra.register.properties.ServiceRegisterProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Component
public class ServerRegistrar {
    @Resource
    private ServiceRegisterProperties config;

    @Resource
    private ConfigurableEnvironment environment;

    private boolean registered = false; // 注册状态
    private int failureCount = 0; // 注册失败计数器

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(ServerRegistrar.class);

    @PostConstruct
    public void registerService() {

        if (config.isEnabled()) {
            log.info("开始注册服务...");
            register();
        }
    }

    /**
     * 执行服务注册
     */
    public void register() {
        HealthCheck healthCheck = config.getHealthCheck();

        String path = environment.getProperty("server.servlet.context-path");

        if(ObjectUtils.isNotEmpty(path) || "/".equals(path)){
            String endpoint = path + healthCheck.getEndpoint();
            healthCheck.setEndpoint(endpoint);
        }

        // 根据地址获取模式设置地址信息
        if (AddressModeConstants.CLIENT.equals(healthCheck.getAddressMode())) {
            healthCheck.setAddress(IpUtils.getHostIp());
            log.info("客户端模式：自动获取地址 {}", healthCheck.getAddress());
        }

        if (AddressModeConstants.CONFIG.equals(healthCheck.getAddressMode())) {
            healthCheck.setAddress(IpUtils.getHostIp());
            log.info("手动配置模式：使用配置中的地址 {}", healthCheck.getAddress());
        }

        if (AddressModeConstants.SERVER.equals(healthCheck.getAddressMode())) {
            healthCheck.setAddress(IpUtils.getHostIp());
            log.info("服务端自动获取地址~");
        }

        // 准备注册请求体
        RegisterBodyDto registerBody = new RegisterBodyDto();
        registerBody.setHealthCheck(healthCheck);
        registerBody.setServiceUniqueKey(config.getKey());
        registerBody.setServiceUniqueName(config.getName());

        try {
            // 发送注册请求
            String requestBody = JSONUtil.toJsonStr(registerBody);
            HttpResponse response = HttpRequest.post(config.getRegistration().getUrl())
                    .timeout(2 * 60 * 1000)  // 设置超时时间为2分钟
                    .header("Content-Type", "application/json;charset=utf-8")
                    .body(requestBody)
                    .execute();

            String responseBodyStr = response.body();
            JsonNode responseBody = objectMapper.readTree(responseBodyStr);
            int code = responseBody.get("code").asInt();

            if (code == 200) {
                registered = true; // 标记注册成功
                failureCount = 0; // 重置注册失败计数器
                log.info("服务注册成功 => {}", responseBodyStr);
            } else {
                handleRegistrationFailure(responseBodyStr);
            }
        } catch (Exception e) {
            handleRegistrationFailure(e.getMessage());
        }
    }

    /**
     * 处理注册失败情况
     *
     * @param errorMessage 失败信息
     */
    private void handleRegistrationFailure(String errorMessage) {
        registered = false; // 标记注册失败
        failureCount++; // 增加注册失败计数器
        log.error("服务注册失败: {}", errorMessage); // 记录注册失败日志

        // 如果失败次数达到最大允许次数，则停止注册尝试
        if (failureCount >= config.getRegistration().getMaxInstances()) {
            log.error("服务注册失败 {} 次。停止注册尝试。", config.getRegistration().getMaxInstances());
            // 在这里可以添加额外的处理逻辑，例如发送警报或通知
        }
    }
}
