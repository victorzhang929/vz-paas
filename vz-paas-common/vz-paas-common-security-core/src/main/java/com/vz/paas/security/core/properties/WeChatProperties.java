package com.vz.paas.security.core.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * 微信属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:35:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WeChatProperties extends SocialProperties {

    private String providerId = "weChat";
}
