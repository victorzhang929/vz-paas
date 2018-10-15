package com.vz.paas.security.core.social.qq.connet;

import com.vz.paas.security.core.social.qq.api.QQ;
import com.vz.paas.security.core.social.qq.api.QQUser;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQ调试器
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:51:24
 */
public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUser user = qq.getUser();

        connectionValues.setDisplayName(user.getNickname());
        connectionValues.setImageUrl(user.getFigureUrlQq1());
        connectionValues.setProfileUrl(null);
        connectionValues.setProviderUserId(user.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {
    }
}
