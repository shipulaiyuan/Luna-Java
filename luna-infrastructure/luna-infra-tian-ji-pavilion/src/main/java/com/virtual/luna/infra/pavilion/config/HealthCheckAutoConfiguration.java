package com.virtual.luna.infra.pavilion.config;


import com.virtual.luna.infra.pavilion.filter.HealthCheckFilter;
import com.virtual.luna.infra.pavilion.properties.ServiceRegisterProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@EnableConfigurationProperties(ServiceRegisterProperties.class)
public class HealthCheckAutoConfiguration {

    @Resource
    private ServiceRegisterProperties config;

    @Bean
    public FilterRegistrationBean<HealthCheckFilter> healthCheckFilter(ServiceRegisterProperties config) {
        FilterRegistrationBean<HealthCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HealthCheckFilter(config.getHealthCheck().getEndpoint())); // 使用 endpoint 而不是 address
        registrationBean.addUrlPatterns(config.getHealthCheck().getEndpoint()); // 使用 endpoint
        return registrationBean;
    }
}