package com.vz.paas.security.core.properties;

import lombok.Data;

/**
 * 社交登录属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:33:57
 */
@Data
public class SocialProperties {

    /**
     * 社交登录拦截url
     */
    private String filterProcessesUtl = "/auth";

    /**
     * QQ
     */
    private QQProperties qq = new QQProperties();

    /**
     * 微信
     */
    private WeChatProperties weChat = new WeChatProperties();
}
