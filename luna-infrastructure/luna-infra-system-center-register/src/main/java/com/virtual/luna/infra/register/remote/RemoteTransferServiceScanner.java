package com.virtual.luna.infra.register.remote;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;

/**
 * 自定义的扫描器，用于扫描带有特定注解的接口
 */
public class RemoteTransferServiceScanner extends ClassPathBeanDefinitionScanner {

    public RemoteTransferServiceScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 判断是否是候选组件（接口）
     */
    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        return metadataReader.getClassMetadata().isInterface();
    }

    /**
     * 判断是否是候选组件（接口）
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }

    /**
     * 执行扫描，并返回符合条件的 BeanDefinitionHolder 集合
     */
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }
}
