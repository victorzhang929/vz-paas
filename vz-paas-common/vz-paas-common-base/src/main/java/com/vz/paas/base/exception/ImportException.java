package com.vz.paas.base.exception;

/**
 * 导入异常类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 11:24:28
 */
public class ImportException extends RuntimeException {

    private static final long serialVersionUID = -4740091660440744697L;

    public ImportException(String message) {
        super(message);
    }
}
