package com.virtual.luna.framework.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

@ConfigurationProperties(prefix = "luna.security")
public class SecurityProperties {

    /**
     * HTTP 请求时，访问令牌的请求 Header
     */
    private String tokenHeader = "Authorization";

    /**
     * HTTP 请求时，访问令牌的请求参数
     */
    private String tokenParameter = "token";

    /**
     * jwt 密钥
     */
    private String jwtSecret = "jwt-secret";

    /**
     * mock 模式的开关
     */
    private Boolean mockEnable = false;
    /**
     * mock 模式的密钥
     * 一定要配置密钥，保证安全性
     */
    private String mockSecret = "test";

    /**
     * 免登录的 URL 列表
     */
    private List<String> permitAllUrls = Collections.emptyList();

    /**
     * PasswordEncoder 加密复杂度，越高开销越大
     */
    private Integer passwordEncoderLength = 4;

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getTokenParameter() {
        return tokenParameter;
    }

    public void setTokenParameter(String tokenParameter) {
        this.tokenParameter = tokenParameter;
    }

    public Boolean getMockEnable() {
        return mockEnable;
    }

    public void setMockEnable(Boolean mockEnable) {
        this.mockEnable = mockEnable;
    }

    public String getMockSecret() {
        return mockSecret;
    }

    public void setMockSecret(String mockSecret) {
        this.mockSecret = mockSecret;
    }

    public List<String> getPermitAllUrls() {
        return permitAllUrls;
    }

    public void setPermitAllUrls(List<String> permitAllUrls) {
        this.permitAllUrls = permitAllUrls;
    }

    public Integer getPasswordEncoderLength() {
        return passwordEncoderLength;
    }

    public void setPasswordEncoderLength(Integer passwordEncoderLength) {
        this.passwordEncoderLength = passwordEncoderLength;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
}
