package com.virtual.luna.common.base.domain;

import com.virtual.luna.common.base.enums.GlobalErrorCodeConstants;
import com.virtual.luna.common.base.exception.ErrorCode;
import com.virtual.luna.common.base.exception.LunaException;

import java.io.Serializable;

/**
 * 通用返回
 *
 * @param <T> 数据泛型
 */
public class CommonResult<T> implements Serializable {

    private Integer code; // 错误码
    private T data; // 返回数据
    private String msg; // 错误提示

    /**
     * 根据传入的 CommonResult 对象创建错误结果
     *
     * @param result 传入的 result 对象
     * @param <T>    返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMsg());
    }

    /**
     * 创建带有错误码和错误信息的错误结果
     *
     * @param code    错误码
     * @param message 错误信息
     * @param <T>     返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> CommonResult<T> error(Integer code, String message) {
        return new CommonResult<>(code, null, message);
    }

    /**
     * 根据 ErrorCode 创建错误结果
     *
     * @param errorCode 错误码和错误信息的封装
     * @param <T>       返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> CommonResult<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    /**
     * 根据 LunaException 创建错误结果
     *
     * @param lunaException LunaException 异常
     * @param <T>           返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> CommonResult<T> error(LunaException lunaException) {
        return error(lunaException.getCode(), lunaException.getMessage());
    }

    /**
     * 创建失败结果
     *
     * @param data 返回的数据
     * @param <T>  返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> CommonResult<T> error(T data) {
        return new CommonResult<>(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), data, "Error");
    }

    /**
     * 创建成功结果
     *
     * @param data 返回的数据
     * @param <T>  返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(GlobalErrorCodeConstants.SUCCESS.getCode(), data, "Success");
    }

    public CommonResult() {
    }

    public CommonResult(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
