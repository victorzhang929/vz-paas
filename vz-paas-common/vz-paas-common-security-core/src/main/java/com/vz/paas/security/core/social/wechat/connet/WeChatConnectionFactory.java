package com.vz.paas.security.core.social.wechat.connet;

import com.vz.paas.security.core.social.wechat.api.WeChat;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信连接工厂
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 14:12:23
 */
public class WeChatConnectionFactory extends OAuth2ConnectionFactory<WeChat> {

    public WeChatConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeChatServiceProvider(appId, appSecret), new WeChatAdapter());
    }

    /**
     * 由于微信的openId和accessToken是一起返回的，所以再这里直接根据accessToken设置providerUserId即可
     * 不需要像QQ那样通过QQAdapter来获取
     * @param accessGrant 登录资格
     * @return openId否则null
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WeChatAccessGrant) {
            return ((WeChatAccessGrant) accessGrant).getOpenId();
        }
        return null;
    }

    @Override
    public Connection<WeChat> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(),
                getApiAdapter(extractProviderUserId(accessGrant)));
    }

    @Override
    public Connection<WeChat> createConnection(ConnectionData data) {
        return new OAuth2Connection<>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<WeChat> getApiAdapter(String providerUserId) {
        return new WeChatAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<WeChat> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WeChat>) getServiceProvider();
    }
}
