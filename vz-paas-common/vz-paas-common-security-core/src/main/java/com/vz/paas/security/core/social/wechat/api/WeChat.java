package com.vz.paas.security.core.social.wechat.api;

/**
 * 微信API调用接口
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 18:04:21
 */
public interface WeChat {

    /**
     * 获取微信用户信息
     * @param openId 应用id
     * @return 微信用户信息
     */
    WeChatUser getUser(String openId);
}
