package com.vz.paas.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认短信发送者
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 10:38:29
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code, String ip) {
        log.warn("请配置真实的短信验证码发送器（SmsCodeSender）");

        log.info("向手机" + mobile + "\n发送短信验证码" + code + "\nip:" + ip);
    }
}
