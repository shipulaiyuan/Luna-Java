package com.virtual.luna.framework.security.core.aop;

import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.framework.security.core.annotations.PreAuthenticated;
import com.virtual.luna.framework.security.core.util.SecurityFrameworkUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static com.virtual.luna.common.base.enums.GlobalErrorCodeConstants.UNAUTHORIZED;


@Aspect
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw new LunaException(UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
