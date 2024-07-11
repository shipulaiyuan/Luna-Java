package com.virtual.luna.infra.register.core;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

public class RemoteConfigProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTY_SOURCE_NAME = "remoteConfig";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String configUrl = environment.getProperty("service.config-addr");

        Boolean enabled = Boolean.valueOf(environment.getProperty("service.config-enabled"));

        if(!enabled){
            System.out.println("Remote Configuration Off. Skipping remote config loading.");
            return;
        }

        if (configUrl == null || configUrl.isEmpty()) {
            System.out.println("Service config address is not provided. Skipping remote config loading.");
            return;
        }

        Map<String, Object> propertySource = new HashMap<>();

        try {
            System.out.println("Loading Remote configuration from ServerConfig...");

            HttpResponse response = HttpRequest.get(configUrl)
                    .timeout(2 * 60 * 1000)
                    .execute();

            if (!response.isOk()) {
                throw new RuntimeException("Failed to fetch configuration, HTTP status: " + response.getStatus());
            }

            String ymlString = response.body();
            Yaml yaml = new Yaml();
            Map<String, Object> yamlProperties = yaml.load(ymlString);
            propertySource.putAll(flattenYamlMap(yamlProperties));

            environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));
            System.out.println("Remote configuration loaded successfully");

        } catch (Exception e) {
            System.out.println("Failed to load remote configuration: " + e.getMessage());
            throw new RuntimeException("Failed to load remote configuration", e);
        }
    }

    private Map<String, Object> flattenYamlMap(Map<String, Object> yamlMap) {
        Map<String, Object> flatMap = new HashMap<>();
        flattenYamlMap("", yamlMap, flatMap);
        return flatMap;
    }

    @SuppressWarnings("unchecked")
    private void flattenYamlMap(String prefix, Map<String, Object> yamlMap, Map<String, Object> flatMap) {
        yamlMap.forEach((key, value) -> {
            String fullKey = org.springframework.util.StringUtils.hasText(prefix) ? prefix + "." + key : key;
            if (value instanceof Map) {
                flattenYamlMap(fullKey, (Map<String, Object>) value, flatMap);
            } else {
                flatMap.put(fullKey, value);
            }
        });
    }
}
