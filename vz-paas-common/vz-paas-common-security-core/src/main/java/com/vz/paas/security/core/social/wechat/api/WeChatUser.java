package com.vz.paas.security.core.social.wechat.api;

import lombok.Data;

/**
 * 微信用户模型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 18:04:52
 */
@Data
public class WeChatUser {

    /**
     * 普通用户的标识，对当前开发者账号唯一
     */
    private String openid;

    /**
     * 普通用户昵称
     */
    private String nickname;

    /**
     * 语言
     */
    private String language;

    /**
     * 普通用户性别，1：男性；2：女性
     */
    private String sex;

    /**
     * 普通用户个人资料填写的省份
     */
    private String province;

    /**
     * 普通用户个人写资料填写的城市
     */
    private String city;

    /**
     * 国家，如中国CN
     */
    private String country;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像）
     * 用户没有头像时该项为空
     */
    private String headimgurl;

    /**
     * 用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
     */
    private String[] privilege;

    /**
     * 用户统一标识，针对一个微信开发平台账号下的应用，同一用户的unionid是唯一的
     */
    private String unionid;
}
