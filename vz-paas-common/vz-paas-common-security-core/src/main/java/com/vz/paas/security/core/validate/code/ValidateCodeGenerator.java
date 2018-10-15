package com.vz.paas.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 16:33:25
 */
public interface ValidateCodeGenerator {

    /**
     * 生成验证码
     * @param request 请求
     * @return 验证码
     */
    ValidateCode generate(ServletWebRequest request);
}
