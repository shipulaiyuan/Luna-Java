package com.virtual.luna.infra.register.core;


import com.virtual.luna.infra.register.properties.HealthCheck;

public class RegisterBodyDto {

    private String serviceUniqueKey;

    private String serviceUniqueName;

    private HealthCheck healthCheck;

    public String getServiceUniqueName() {
        return serviceUniqueName;
    }

    public void setServiceUniqueName(String serviceUniqueName) {
        this.serviceUniqueName = serviceUniqueName;
    }

    public String getServiceUniqueKey() {
        return serviceUniqueKey;
    }

    public void setServiceUniqueKey(String serviceUniqueKey) {
        this.serviceUniqueKey = serviceUniqueKey;
    }

    public HealthCheck getHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(HealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }
}
