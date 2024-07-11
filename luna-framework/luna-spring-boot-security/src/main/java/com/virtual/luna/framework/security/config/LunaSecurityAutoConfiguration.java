package com.virtual.luna.framework.security.config;

import com.virtual.luna.framework.security.core.aop.PreAuthenticatedAspect;
import com.virtual.luna.framework.security.core.filter.TokenAuthenticationFilter;
import com.virtual.luna.framework.security.core.filter.TokenService;
import com.virtual.luna.framework.security.core.handle.AuthenticationEntryPointImpl;
import com.virtual.luna.framework.security.properties.SecurityProperties;
import com.virtual.luna.framework.web.core.handler.GlobalExceptionHandler;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;


@AutoConfiguration
@AutoConfigureOrder(-1)
@EnableConfigurationProperties(SecurityProperties.class)
public class LunaSecurityAutoConfiguration {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private TokenService tokenService;

    /**
     * TokenService Jwt解析处理
     */
    @Bean
    public TokenService tokenService() {
        return new TokenService();
    }

    /**
     * 处理用户未登录拦截的切面的 Bean
     */
    @Bean
    public PreAuthenticatedAspect preAuthenticatedAspect() {
        return new PreAuthenticatedAspect();
    }

    /**
     * 认证失败处理类 Bean
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 权限不够处理器 Bean
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * Spring Security 加密器
     * 考虑到安全性，这里采用 BCryptPasswordEncoder 加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }

    /**
     * Token 认证过滤器 Bean
     */
    @Bean
    public TokenAuthenticationFilter authenticationTokenFilter(GlobalExceptionHandler globalExceptionHandler) {
        return new TokenAuthenticationFilter(securityProperties, globalExceptionHandler,tokenService);
    }

}
