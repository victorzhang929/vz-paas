package com.vz.paas.security.core.properties;

/**
 * 安全常量
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:38:41
 */
public interface SecurityConstant {

    /**
     * 默认处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/auth/code";

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/auth/require";

    /**
     * 默认用户名密码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/auth/form";

    /**
     * 默认手机验证码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "auth/mobile";

    /**
     * 发送短信验证码或验证短信验证码时，传递手机号参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 默认OPENID登录请求处理的url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "auth/openid";

    /**
     * OPENID参数名
     */
    String DEFAULT_PARAMETER_NAME_OPENID = "openId";

    /**
     * 验证图片验证码时，HTTP请求中默认的携带图片验证码信息的参数名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * 验证短信验证码时，HTTP请求中默认的携带短信验证码信息的参数名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * 验证邮箱验证码时，HTTP请求中默认携带短信验证码信息的参数名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_EMAIL = "emailCode";

    /**
     * 发送邮箱验证码或验证邮箱验证码时，传递邮箱的参数的名称
     * 发送邮箱验证码或验证邮箱验证码时，传递邮箱的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_EMAIL = "email";

    /**
     * providerId参数名
     */
    String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";

    /**
     * 获取第三方用户信息的url
     */
    String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";

}
