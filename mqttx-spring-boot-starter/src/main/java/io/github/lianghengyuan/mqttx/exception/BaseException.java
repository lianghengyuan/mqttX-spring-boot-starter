package io.github.lianghengyuan.mqttx.exception;

public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 1L;


    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BaseException(String code, Object[] args, String defaultMessage)
    {
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String code, Object[] args)
    {
        this( code, args, null);
    }

    public BaseException(String defaultMessage)
    {
        this(null, null, defaultMessage);
    }

    public String getCode()
    {
        return code;
    }

    public Object[] getArgs()
    {
        return args;
    }

    public String getDefaultMessage()
    {
        return defaultMessage;
    }
}
