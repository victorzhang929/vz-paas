package com.vz.paas.util.exception;

/**
 * HttpAes异常类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 11:40:14
 */
public class HttpAesException extends IllegalArgumentException {

    private static final long serialVersionUID = -7542052571412854133L;

    public HttpAesException(String message) {
        super(message);
    }

    public HttpAesException() {}
}
