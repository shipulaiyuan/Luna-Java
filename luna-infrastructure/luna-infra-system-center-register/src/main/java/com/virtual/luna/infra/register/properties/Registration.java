package com.virtual.luna.infra.register.properties;

public class Registration {

    private String url;
    private long interval_ms;
    private long maxInstances;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getInterval_ms() {
        return interval_ms;
    }

    public void setInterval_ms(long interval_ms) {
        this.interval_ms = interval_ms;
    }

    public long getMaxInstances() {
        return maxInstances;
    }

    public void setMaxInstances(long maxInstances) {
        this.maxInstances = maxInstances;
    }
}
