package com.vz.paas.security.core;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 安全结果
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 09:54:16
 */
@Data
public class SecurityResult<T> {

    public static final int SUCCESS_CODE = 200;

    public static final String SUCCESS_MESSAGE = "操作成功";

    public static final int ERROR_CODE = 500;

    public static final String ERROR_MESSAGE = "内部异常";

    private Integer code;

    private String message;

    private T result;

    public SecurityResult(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public SecurityResult() {}

    private SecurityResult(T result) {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
        this.result = result;
    }


    public static <T> SecurityResult<T> ok(T result) {
        return new SecurityResult(result);
    }

    public static SecurityResult ok() {
        return new SecurityResult(null);
    }

    public static SecurityResult error(String message) {
        return error(message, null);
    }

    public static <T> SecurityResult<T> error(String message, T result) {
        return new SecurityResult(ERROR_CODE, StringUtils.isEmpty(message) ? ERROR_MESSAGE : message, result);
    }
}
