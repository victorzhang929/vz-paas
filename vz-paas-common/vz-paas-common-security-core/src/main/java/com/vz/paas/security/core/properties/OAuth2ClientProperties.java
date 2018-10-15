package com.vz.paas.security.core.properties;

import lombok.Data;

/**
 * OAuth2客户端属性，认证服务器注册的第三方应用配置项
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:22:37
 */
@Data
public class OAuth2ClientProperties {

    /**
     * 第三方应用appId
     */
    private String clientId;

    /**
     * 第三方应用appSecret
     */
    private String clientSecret;

    /**
     * 针对此应用发出的token的有效时间，默认2小时
     */
    private int accessTokenValidateSeconds = 2 * 60 * 60;

    /**
     * 刷新token合法时间，默认为一个月
     */
    private int refreshTokenValiditySeconds = 30 * 24 * 60 * 60;

    /**
     * 应用范围
     */
    private String scope;
}
