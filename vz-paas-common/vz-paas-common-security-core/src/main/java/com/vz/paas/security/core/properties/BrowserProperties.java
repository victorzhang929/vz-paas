package com.vz.paas.security.core.properties;


import lombok.Data;

/**
 * 浏览器属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:08:13
 */
@Data
public class BrowserProperties {

    /**
     * session管理属性
     */
    private SessionProperties session = new SessionProperties();

    /**
     * 记住我 功能有效时，默认为1小时
     */
    private int rememberMeSeconds = 3600;

    /**
     * 社交登录，如果需要用户注册，跳转的也秒
     */
    private String signUpUrl = "/pc-signUp.html";

    /**
     * 登录响应的方式，默认为json
     */
    private LoginResponseType type = LoginResponseType.JSON;

    /**
     * 登录成功后跳转的地址，如果设置了此属性，则登录成功后总是会跳到这个位置
     * 只在type为REDIRECT时生效
     */
    private String signInSuccessUrl;
}
