package com.virtual.luna.common.base.exception;

/**
 * 业务异常
 * 
 * @author shi
 */
public final class LunaException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public LunaException()
    {
    }

    public LunaException(String message)
    {
        this.message = message;
    }

    public LunaException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public LunaException(ErrorCode errorCode)
    {
        this.message = errorCode.getMsg();
        this.code = errorCode.getCode();
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public LunaException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public LunaException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }
}