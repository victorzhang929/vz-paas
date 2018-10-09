package com.vz.paas.feign;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全客户端配置信息
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 18:26:56
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
