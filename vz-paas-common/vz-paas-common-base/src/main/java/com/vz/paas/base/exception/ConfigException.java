package com.vz.paas.base.exception;

/**
 * 配置异常类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 11:22:41
 */
public class ConfigException extends RuntimeException {

    private static final long serialVersionUID = 6480772904575978373L;

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException() {}
}
