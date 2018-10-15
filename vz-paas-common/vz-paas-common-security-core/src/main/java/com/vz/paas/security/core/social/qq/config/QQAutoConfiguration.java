package com.vz.paas.security.core.social.qq.config;

import com.vz.paas.security.core.properties.QQProperties;
import com.vz.paas.security.core.properties.SecurityProperties;
import com.vz.paas.security.core.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * QQ自动配置类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:37:16
 */
@Configuration
@ConditionalOnProperty(prefix = "paas.security.social.qq", name = "api-id")
public class QQAutoConfiguration  extends SocialAutoConfigurerAdapter {

    private final SecurityProperties securityProperties;

    @Autowired
    public QQAutoConfiguration(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
    }

}
