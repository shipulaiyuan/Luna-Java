package com.virtual.luna.infra.system.security;

import com.virtual.luna.framework.security.config.AuthorizeRequestsCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

/**
 * SpringSecurity 的自定义权限
 *
 */
@Component
public class SystemCenterAuthorizeRequestsCustomizer extends AuthorizeRequestsCustomizer {

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        // Swagger 接口文档
        registry.requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/*/api-docs").permitAll();
        // Spring Boot Actuator 的安全配置
        registry.requestMatchers("/actuator").permitAll()
                .requestMatchers("/actuator/**").permitAll();
        // Druid 监控
        registry.requestMatchers("/druid/**").permitAll();


//        registry.requestMatchers(getWebPrefix(1,"/server-config/**")).permitAll();
    }



}
