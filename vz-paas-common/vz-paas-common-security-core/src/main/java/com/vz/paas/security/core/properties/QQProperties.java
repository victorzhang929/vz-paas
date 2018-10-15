package com.vz.paas.security.core.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * QQ登录属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:33:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QQProperties extends SocialProperties {

    /**
     * 第三方ID，用来决定发起第三方登录的url，默认为qq
     */
    private String providerId = "qq";
}
