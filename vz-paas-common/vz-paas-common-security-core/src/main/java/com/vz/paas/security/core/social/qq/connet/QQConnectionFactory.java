package com.vz.paas.security.core.social.qq.connet;

import com.vz.paas.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * QQ连接工厂
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:53:34
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
