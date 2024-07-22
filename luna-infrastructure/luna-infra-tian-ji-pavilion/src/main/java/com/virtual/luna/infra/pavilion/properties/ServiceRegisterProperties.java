package com.virtual.luna.infra.pavilion.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service")
public class ServiceRegisterProperties {

    private boolean enabled;
    private String name;
    private String key;
    private Registration registration;
    private HealthCheck healthCheck;
    private String configAddr;
    private boolean configEnabled;

    public boolean isEnabled() {
        return enabled;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public HealthCheck getHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(HealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }

    public String getConfigAddr() {
        return configAddr;
    }

    public void setConfigAddr(String configAddr) {
        this.configAddr = configAddr;
    }

    public boolean isConfigEnabled() {
        return configEnabled;
    }

    public void setConfigEnabled(boolean configEnabled) {
        this.configEnabled = configEnabled;
    }
}
