package com.virtual.luna.infra.register.remote;

import com.virtual.luna.infra.register.annotation.RemoteTransferService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BaPoInvoker implements ApplicationRunner {

    @Resource
    private BaPoScanner remoteServiceScanner;

    @Resource
    private BaPoFactory remoteServiceFactory;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<Class<?>> remoteServiceInterfaces = remoteServiceScanner.scan("com.virtual.luna.module.**.remote");

        for (Class<?> remoteServiceInterface : remoteServiceInterfaces) {
            if (remoteServiceInterface.isAnnotationPresent(RemoteTransferService.class)) {

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
