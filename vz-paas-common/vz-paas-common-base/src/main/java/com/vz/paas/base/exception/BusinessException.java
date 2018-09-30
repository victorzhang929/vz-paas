package com.vz.paas.base.exception;

/**
 * 业务异常类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 11:25:55
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 3160241586346324994L;
    protected int code;

    public BusinessException() {}

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, String message, Object... args) {
        super(String.format(message, args));
        this.code = code;
    }

    public BusinessException(ErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.message(), args));
        this.code = codeEnum.code();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
