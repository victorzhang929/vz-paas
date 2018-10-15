package com.vz.paas.security.core.properties;

import lombok.Data;

/**
 * 验证码属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:51:32
 */
@Data
public class ValidateCodeProperties {

    /**
     * 图片验证码属性
     */
    private ImageCodeProperties image = new ImageCodeProperties();

    /**
     * 短信验证码属性
     */
    private SmsCodeProperties sms = new SmsCodeProperties();

    /**
     * 邮箱验证码属性
     */
    private EmailCodeProperties email = new EmailCodeProperties();
}
