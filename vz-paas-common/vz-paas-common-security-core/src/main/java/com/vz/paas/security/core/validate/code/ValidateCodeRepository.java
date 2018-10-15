package com.vz.paas.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 16:17:22
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param request 请求
     * @param code 验证码对象
     * @param validateCodeType 类型
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     * @param request 请求
     * @param validateCodeType 类型
     * @return 验证码
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     * @param request 请求
     * @param validateCodeType 类型
     */
    void remove(ServletWebRequest request, ValidateCodeType validateCodeType);
}
