package com.vz.paas.security.core.social.qq.api;

import java.io.Serializable;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * QQ用户模型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:16:15
 */
@Data
public class QQUser implements Serializable {
    private static final long serialVersionUID = -4699588911787931679L;

    /**
     * 返回码
     */
    private String ret;

    /**
     * 如果是ret<0，会有相应的错误信息提示，返回数据全部用utf-8编码
     */
    private String msg;

    private String openId;

    /**
     * 是否丢失
     */
    @JsonProperty("is_lost")
    private String isLost;

    /**
     * 省（直辖市）
     */
    private String province;

    /**
     * 市（直辖市区）
     */
    private String city;

    /**
     * 出生年月
     */
    private String year;

    /**
     * QQ空间昵称
     */
    private String nickname;

    /**
     * 大小为30*30像素的QQ空间头像
     */
    @JsonProperty("figureurl")
    private String figureUrl;

    /**
     * URL大小为50*50像素的QQ空间头像URL
     */
    @JsonProperty("figureurl_1")
    private String figureUrl1;

    /**
     * URL大小为100*100像素的QQ空间头像URL
     */
    @JsonProperty("figureurl_2")
    private String figureUrl2;

    /**
     * 大小为40×40像素的QQ头像URL。
     */
    @JsonProperty("figureurl_qq_1")
    private String figureUrlQq1;

    /**
     * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100×100的头像，但40×40像素则是一定会有。
     */
    @JsonProperty("figureurl_qq_2")
    private String figureUrlQq2;

    /**
     * 性别，如果获取不到默认为男
     */
    private String gender;

    /**
     * 标识用户是否为黄钻用户（0：不是；1：是）。
     */
    @JsonProperty("is_yellow_vip")
    private String isYellowVip;

    /**
     * 标识用户是否为vip用户（0：不是；1：是）
     */
    private String vip;

    /**
     * 黄钻等级
     */
    @JsonProperty("yellow_vip_level")
    private String yellowVipLevel;

    /**
     * 等级
     */
    private String level;

    /**
     * 标识是否为年费黄钻用户（0：不是；1：是）
     */
    @JsonProperty("is_yellow_year_vip")
    private String isYellowYearVip;
}
