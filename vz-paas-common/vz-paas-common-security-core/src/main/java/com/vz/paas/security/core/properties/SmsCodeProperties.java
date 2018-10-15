package com.vz.paas.security.core.properties;

import lombok.Data;

/**
 * 短信验证码属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:18:34
 */
@Data
public class SmsCodeProperties {

    /**
     * 验证码长度
     */
    private int length = 6;

    /**
     * 过期时间，1分钟
     */
    private int expireIn = 60;

    /**
     * 拦截的url，多个url用逗号隔开
     */
    private String url;

    /**
     * 每天每个手机号码最大发送短信的数量
     */
    private int mobileMaxSendCount;

    /**
     * 每天每个IP最大发送短信的数量
     */
    private int ipMaxSendCount;

    /**
     * 每天最大发送短信的数量
     */
    private int totalMaxSendCount;
}
