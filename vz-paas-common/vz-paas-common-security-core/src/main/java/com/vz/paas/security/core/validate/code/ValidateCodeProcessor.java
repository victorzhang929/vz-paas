package com.vz.paas.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器，封装不同验证码的处理逻辑
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 16:23:47
 */
public interface ValidateCodeProcessor {

    /**
     * 创建验证码
     * @param request 请求
     * @throws Exception 异常
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 验证码（验证后删除）
     * @param request 请求
     */
    void validate(ServletWebRequest request);

    /**
     * 验证码（验证后不删除）
     * @param request 请求
     */
    void check(ServletWebRequest request);
}
