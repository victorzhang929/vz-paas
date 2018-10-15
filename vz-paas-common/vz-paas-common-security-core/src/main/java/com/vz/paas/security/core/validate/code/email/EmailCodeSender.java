package com.vz.paas.security.core.validate.code.email;

/**
 * 邮件验证码发送者
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 11:10:16
 */
public interface EmailCodeSender {

    /**
     * 发送邮件验证码
     * @param email 邮件
     * @param code 验证码
     */
    void send(String email, String code);
}
