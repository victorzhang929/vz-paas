package com.vz.paas.security.core.social.wechat;

import com.vz.paas.security.core.properties.SecurityProperties;
import com.vz.paas.security.core.properties.WeChatProperties;
import com.vz.paas.security.core.social.view.PcConnectView;
import com.vz.paas.security.core.social.wechat.connet.WeChatConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 14:27:39
 */
@Configuration
@ConditionalOnProperty(prefix = "paas.security.social.wechat", name = "app-id")
public class WeChatAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties security;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeChatProperties properties = security.getSocial().getWeChat();
        return new WeChatConnectionFactory(properties.getProviderId(), properties.getAppId(), properties.getAppSecret());
    }

    @Bean({"connect/wechatConnect", "connect/wechatConnected"})
    @ConditionalOnMissingBean(name = "wechatConnectedView")
    public View wechatConnectedView() {
        return new PcConnectView();
    }
}
