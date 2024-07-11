package com.virtual.luna.infra.register.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class LocalConfigProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTY_SOURCE_NAME = "localConfig";
    private static final String LOCAL_CONFIG_PATH = "classpath:application.yaml";
    private static final String PROFILE_CONFIG_PATH_PATTERN = "classpath:application-%s.yaml";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            // 加载 application.yml
            PropertySource<?> localConfig = new ResourcePropertySource(PROPERTY_SOURCE_NAME, LOCAL_CONFIG_PATH);
            environment.getPropertySources().addLast(localConfig);
            System.out.println("Loaded local configuration from " + LOCAL_CONFIG_PATH);

            // 加载环境特定的 application-{profile}.yml
            String[] activeProfiles = environment.getActiveProfiles();
            for (String profile : activeProfiles) {
                String profileConfigPath = String.format(PROFILE_CONFIG_PATH_PATTERN, profile);
                PropertySource<?> profileConfig = new ResourcePropertySource(PROPERTY_SOURCE_NAME + "-" + profile, profileConfigPath);
                environment.getPropertySources().addLast(profileConfig);
                System.out.println("Loaded environment-specific configuration from " + profileConfigPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load local configuration", e);
        }
    }
}
