package com.vz.paas.base.exception;

/**
 * 格式转换异常
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:10:03
 */
public class BooleanParseException extends RuntimeException {

    public BooleanParseException() {
        super();
    }

    public BooleanParseException(String message) {
        super(message);
    }

    public BooleanParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BooleanParseException(Throwable cause) {
        super(cause);
    }
}
