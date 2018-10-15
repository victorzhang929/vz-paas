package com.vz.paas.security.core.social;

import com.vz.paas.security.core.social.support.SocialUser;
import org.springframework.social.connect.Connection;

/**
 * 基础社交登录控制器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:01:08
 */
public abstract class BaeSocialController {

    /**
     * 根据Connection信息构建SocialUser
     * @param connection 连接器
     * @return SocialUser
     */
    protected SocialUser buildSocialUser(Connection<?> connection) {
        SocialUser user = new SocialUser();
        user.setProviderId(connection.getKey().getProviderId());
        user.setProviderUserId(connection.getKey().getProviderUserId());
        user.setNickName(connection.getDisplayName());
        user.setHeadImg(connection.getImageUrl());

        return user;
    }
}
