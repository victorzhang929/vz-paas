package com.vz.paas.security.core.social.support;

import lombok.Data;

/**
 * 社交用户模型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 16:29:36
 */
@Data
public class SocialUser {

    /**
     * 提供者id
     */
    private String providerId;

    /**
     * 提供者用户id
     */
    private String providerUserId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImg;
}
