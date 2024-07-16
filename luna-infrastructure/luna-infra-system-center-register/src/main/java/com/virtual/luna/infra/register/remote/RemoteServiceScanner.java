package com.virtual.luna.infra.register.remote;

import com.virtual.luna.infra.register.annotation.RemoteTransferService;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RemoteServiceScanner {
    private final RemoteTransferServiceScanner scanner;

    public RemoteServiceScanner(DefaultListableBeanFactory beanFactory) {
        this.scanner = new RemoteTransferServiceScanner(beanFactory);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RemoteTransferService.class));
    }

    /**
     * 扫描指定基础包下带有 @RemoteTransferService 注解的接口和类
     *
     * @param basePackage 扫描的基础包路径
     * @return 符合条件的接口和类集合
     */
    public Set<Class<?>> scan(String basePackage) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = scanner.doScan(basePackage);
        Set<Class<?>> remoteServiceComponents = new HashSet<>();

        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            try {
                Class<?> beanClass = Class.forName(holder.getBeanDefinition().getBeanClassName());
                remoteServiceComponents.add(beanClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return remoteServiceComponents;
    }
}
