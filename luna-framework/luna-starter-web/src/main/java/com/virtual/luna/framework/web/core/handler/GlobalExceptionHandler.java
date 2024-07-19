package com.virtual.luna.framework.web.core.handler;

import com.virtual.luna.common.base.domain.CommonResult;
import com.virtual.luna.common.base.utils.StringUtils;
import com.virtual.luna.common.base.exception.LunaException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 *
 * @author shi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                    HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return CommonResult.error("请求方式不支持。" , e.getMethod());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public CommonResult<String> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        String message = e.getMessage();
        return CommonResult.error("服务器内部错误，请联系管理员。" , message);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<String> handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        String message = e.getMessage();
        return CommonResult.error("服务器内部错误，请联系管理员。" , message);
    }

    /**
     * Luna 业务异常
     */
    @ExceptionHandler(LunaException.class)
    public CommonResult handleLunaException(LunaException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? CommonResult.error(code, e.getMessage()) : CommonResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<String> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return CommonResult.error("参数绑定失败。" , message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return CommonResult.error("参数验证失败。" , message);
    }

    /**
     * 请求参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',缺少请求参数: '{}'", requestURI, e.getParameterName());
        return CommonResult.error("缺少请求参数。" , e.getParameterName());
    }

    /**
     * 请求体读取异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',请求体不可读", requestURI, e);
        return CommonResult.error("请求体不可读。", e.getMessage());
    }
}


