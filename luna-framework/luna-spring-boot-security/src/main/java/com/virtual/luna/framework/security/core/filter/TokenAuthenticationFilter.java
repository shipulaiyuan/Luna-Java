package com.virtual.luna.framework.security.core.filter;

import cn.hutool.core.util.StrUtil;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.framework.security.core.user.LoginUser;
import com.virtual.luna.framework.security.core.util.SecurityFrameworkUtils;
import com.virtual.luna.framework.security.properties.SecurityProperties;
import com.virtual.luna.framework.web.core.handler.GlobalExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Token 过滤器，验证 token 的有效性
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final GlobalExceptionHandler globalExceptionHandler;

    private final TokenService tokenService;

    public TokenAuthenticationFilter(SecurityProperties securityProperties
            , GlobalExceptionHandler globalExceptionHandler
            , TokenService tokenService) {
        this.securityProperties = securityProperties;
        this.globalExceptionHandler = globalExceptionHandler;
        this.tokenService = tokenService;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotEmpty(token)) {
            try {
                LoginUser loginUser = tokenService.buildLoginUserByToken(token);

                // 日常开发调试
                if (loginUser == null) {
                    loginUser = mockLoginUser(request, token);
                }

                // 设置当前用户
                if (loginUser != null) {
                    SecurityFrameworkUtils.setLoginUser(loginUser, request);
                }

            } catch (Exception e) {
                new LunaException("Jwt 构建用户失败");
            }
        }

        // 继续过滤链
        chain.doFilter(request, response);
    }


    /**
     * 模拟登录用户，方便日常开发调试
     *
     * @param request 请求
     * @param token 模拟的 token，格式为 {@link SecurityProperties#getMockSecret()} + 用户编号
     * @return 模拟的 LoginUser
     */
    private LoginUser mockLoginUser(HttpServletRequest request, String token) {
        if (!securityProperties.getMockEnable()) {
            return null;
        }
        // 必须以 mockSecret 开头
        if (!token.startsWith(securityProperties.getMockSecret())) {
            return null;
        }
        // 构建模拟用户
        Long userId = 1L;

        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(userId);
        loginUser.setIdentity(userId);

        return loginUser;
    }

}
