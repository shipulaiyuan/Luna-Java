package com.virtual.luna.framework.email.domin;

public class SmsRequest {
    private String phone;
    private String templateCode;
    private String signName;
    private String templateParam;

    // 构造方法
    public SmsRequest(String phone, String templateCode, String signName, String templateParam) {
        this.phone = phone;
        this.templateCode = templateCode;
        this.signName = signName;
        this.templateParam = templateParam;
    }

    // Getter和Setter方法
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}

