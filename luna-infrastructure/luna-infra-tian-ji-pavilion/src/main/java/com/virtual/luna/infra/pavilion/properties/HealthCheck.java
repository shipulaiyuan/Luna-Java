package com.virtual.luna.infra.pavilion.properties;

public class HealthCheck {

    private String endpoint;
    private long intervalMs;
    private String addressMode;
    private String address;
    private String port;
    private boolean enabled;

    public String getAddressMode() {
        return addressMode;
    }

    public void setAddressMode(String addressMode) {
        this.addressMode = addressMode;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public long getIntervalMs() {
        return intervalMs;
    }

    public void setIntervalMs(long intervalMs) {
        this.intervalMs = intervalMs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
