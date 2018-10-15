package com.vz.paas.security.core.properties;

import lombok.Data;

/**
 * OAuth2属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:31:57
 */
@Data
public class OAuth2Properties {

    /**
     * 使用jwt时为token签名的密钥
     */
    private String jwtSigningKey = "paas";

    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};
}
