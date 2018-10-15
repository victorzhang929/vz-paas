package com.vz.paas.security.core.social.wechat.connet;

import com.vz.paas.security.core.social.wechat.api.WeChat;
import com.vz.paas.security.core.social.wechat.api.WeChatImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 微信OAuth2流程处理器的提供器，供spring social的connect体系调用
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 14:14:30
 */
public class WeChatServiceProvider extends AbstractOAuth2ServiceProvider<WeChat> {


    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";

    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WeChatServiceProvider(String appId, String appSecret) {
        super(new WeChatOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
    }

    @Override
    public WeChat getApi(String token) {
        return new WeChatImpl(token);
    }
}
