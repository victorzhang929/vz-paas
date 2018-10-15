package com.vz.paas.security.feign;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OAuth2客户端属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 11:30:48
 */
@Data
@ConfigurationProperties(prefix = "paas.oauth2.client")
public class OAuth2ClientProperties {

    private String id;
    private String accessTokenUrl;
    private String clientId;
    private String clientSecret;
    private String clientAuthenticationScheme;
}
