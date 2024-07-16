package com.virtual.luna.infra.register.remote;

import com.virtual.luna.infra.register.annotation.RemoteTransferService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RemoteServiceInvoker implements ApplicationRunner {

    @Resource
    private RemoteServiceScanner remoteServiceScanner;

    @Resource
    private RemoteServiceFactory remoteServiceFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<Class<?>> remoteServiceInterfaces = remoteServiceScanner.scan("com.virtual.luna.module.client.remote");

        for (Class<?> remoteServiceInterface : remoteServiceInterfaces) {
            if (remoteServiceInterface.isAnnotationPresent(RemoteTransferService.class)) {

                // 创建实现类并调用远程服务
                 Object remoteService = remoteServiceFactory.create(remoteServiceInterface);
                // 使用 remoteService 调用接口方法等操作

            } else {
                // 处理未带有 @RemoteTransferService 注解的接口
            }
        }
    }
}

