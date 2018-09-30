package com.vz.paas.base.exception;

/**
 * 引用模型空指针异常
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:12:38
 */
public class ReferenceModelNullException extends RuntimeException {

    private static final long serialVersionUID = -318154770875589045L;

    public ReferenceModelNullException(String message) {
        super(message);
    }
}
