package com.vz.paas.security.core.validate.code.email;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认邮件验证码发送者
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 11:11:27
 */
@Slf4j
public class DefaultEmailCodeSender implements EmailCodeSender {
    @Override
    public void send(String email, String code) {
        log.warn("请配置真实的邮件验证码发送器（SmsCodeSender）");

        log.info("向邮件{}发送验证码{}", email, code);
    }
}
