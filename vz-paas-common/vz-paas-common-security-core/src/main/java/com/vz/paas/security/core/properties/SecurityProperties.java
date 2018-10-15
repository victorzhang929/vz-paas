package com.vz.paas.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全属性信息
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:06:27
 */
@Data
@ConfigurationProperties(prefix = "paas.security")
public class SecurityProperties {

    /**
     * 浏览器环境配置
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * 社交配置
     */
    private SocialProperties social = new SocialProperties();

    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oath2 = new OAuth2Properties();

}
