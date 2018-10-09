package com.vz.paas.zk.registry.exception;

/**
 * 注册中心异常
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 16:04:45
 */
public final class RegException extends RuntimeException {

    private static final long serialVersionUID = -1367183984420130639L;

    public RegException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }

    public RegException(final Exception cause) {
        super(cause);
    }
}
