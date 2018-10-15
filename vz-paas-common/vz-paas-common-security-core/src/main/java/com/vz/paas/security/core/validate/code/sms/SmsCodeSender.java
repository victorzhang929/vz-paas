package com.vz.paas.security.core.validate.code.sms;

/**
 * 短信验证码发送者接口
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 10:36:01
 */
public interface SmsCodeSender {

    /**
     * 短信验证码发送
     * @param mobile 电话
     * @param code 验证码
     * @param ip ip地址
     */
    void send(String mobile, String code, String ip);
}
