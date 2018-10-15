package com.vz.paas.security.core.social.wechat.connet;

import com.vz.paas.security.core.social.wechat.api.WeChat;
import com.vz.paas.security.core.social.wechat.api.WeChatUser;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信调试器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 18:17:58
 */
public class WeChatAdapter implements ApiAdapter<WeChat> {

    private String openId;

    public WeChatAdapter() {}

    public WeChatAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(WeChat weChat) {
        return true;
    }

    @Override
    public void setConnectionValues(WeChat weChat, ConnectionValues connectionValues) {
        WeChatUser user = weChat.getUser(openId);
        connectionValues.setProviderUserId(user.getOpenid());
        connectionValues.setDisplayName(user.getNickname());
        connectionValues.setImageUrl(user.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(WeChat weChat) {
        return null;
    }

    @Override
    public void updateStatus(WeChat weChat, String s) {
    }
}
