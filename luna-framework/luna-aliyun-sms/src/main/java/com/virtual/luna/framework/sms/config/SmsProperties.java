package com.virtual.luna.framework.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "luna.sms")
public class SmsProperties {
    private String host;
    private String path;
    private String appcode;
    private String smsSignId;
    private String templateId;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    public String getSmsSignId() {
        return smsSignId;
    }

    public void setSmsSignId(String smsSignId) {
        this.smsSignId = smsSignId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}

