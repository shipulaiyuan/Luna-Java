package com.virtual.luna.infra.register.properties;

public class Registration {

    private String url;
    private String base;
    private String forward;
    private long interval_ms;
    private long maxInstances;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

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
