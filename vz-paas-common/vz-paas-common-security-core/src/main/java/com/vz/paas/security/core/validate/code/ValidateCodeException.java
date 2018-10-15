package com.vz.paas.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 16:29:33
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -8456241288894889263L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
