package com.virtual.luna.framework.security.core.filter;

import java.util.List;

/**
 * jwt实体数据
 */

public class Claims {
    /**
     * 主题
     */
    private String sub;

    /**
     * 签发时间
     */
    private Long iat;

    /**
     * 过期时间
     */
    private Long exp;

    /**
     * JWT ID
     */
    private String jti;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户状态(1:正常;0:禁用)
     */
    private String status;

    /**
     * 用户角色
     */
    private List<String> roles;

    /**
     * 权限列表
     */
    private List<String> permissions;

    public Claims(String sub, Long iat, Long exp, String jti, String userId, String username, String status, List<String> roles, List<String> permissions) {
        this.sub = sub;
        this.iat = iat;
        this.exp = exp;
        this.jti = jti;
        this.userId = userId;
        this.username = username;
        this.status = status;
        this.roles = roles;
        this.permissions = permissions;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public static ClaimsBuilder builder() {
        return new ClaimsBuilder();
    }

    public static final class ClaimsBuilder {
        //主题
        private String sub;

        //签发时间
        private Long iat;

        //过期时间
        private Long exp;

        //JWT ID
        private String jti;

        //用户id
        private String userId;

        //用户名
        private String username;

        private String status;

        //用户角色
        private List<String> roles;

        private List<String> permissions;

        private ClaimsBuilder() {
        }

        public ClaimsBuilder sub(String sub) {
            this.sub = sub;
            return this;
        }

        public ClaimsBuilder iat(Long iat) {
            this.iat = iat;
            return this;
        }

        public ClaimsBuilder exp(Long exp) {
            this.exp = exp;
            return this;
        }

        public ClaimsBuilder jti(String jti) {
            this.jti = jti;
            return this;
        }

        public ClaimsBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ClaimsBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ClaimsBuilder status(String status) {
            this.status = status;
            return this;
        }

        public ClaimsBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public ClaimsBuilder permissions(List<String> permissions) {
            this.permissions = permissions;
            return this;
        }

        public Claims build() {
            return new Claims(
                    this.sub,
                    this.iat,
                    this.exp,
                    this.jti,
                    this.userId,
                    this.username,
                    this.status,
                    this.roles,
                    this.permissions);
        }
    }
}
