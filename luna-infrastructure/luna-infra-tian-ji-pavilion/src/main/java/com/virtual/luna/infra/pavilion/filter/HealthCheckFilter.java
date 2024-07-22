package com.virtual.luna.infra.pavilion.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HealthCheckFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(HealthCheckFilter.class);
    private final String healthCheckApi;

    public HealthCheckFilter(String healthCheckApi) {
        this.healthCheckApi = healthCheckApi;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().write("OK");
    }

    @Override
    public void destroy() {

    }
}