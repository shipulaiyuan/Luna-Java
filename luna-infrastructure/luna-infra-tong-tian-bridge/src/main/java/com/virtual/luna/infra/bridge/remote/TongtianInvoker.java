package com.virtual.luna.infra.bridge.remote;

import com.virtual.luna.infra.bridge.annotation.TongtianBridge;
import jakarta.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TongtianInvoker implements ApplicationRunner {

    @Resource
    private TongtianScanner remoteServiceScanner;

    @Resource
    private TongtianFactory remoteServiceFactory;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<Class<?>> remoteServiceInterfaces = remoteServiceScanner.scan("com.virtual.luna.module.**.remote");

        for (Class<?> remoteServiceInterface : remoteServiceInterfaces) {
            if (remoteServiceInterface.isAnnotationPresent(TongtianBridge.class)) {

                // 创建实现类并调用远程服务
                Object remoteService = remoteServiceFactory.create(remoteServiceInterface);

                try {
                    applicationContext.getAutowireCapableBeanFactory().autowireBean(remoteService);
                    applicationContext.getAutowireCapableBeanFactory().initializeBean(remoteService, remoteServiceInterface.getSimpleName());
                } catch (BeansException e) {
                    throw new RuntimeException("Failed to register remote service bean", e);
                }
            } else {
                throw new RuntimeException("Failed to register remote service bean");
            }
        }
    }
}
