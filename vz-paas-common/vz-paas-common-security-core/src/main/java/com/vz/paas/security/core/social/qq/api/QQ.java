package com.vz.paas.security.core.social.qq.api;

/**
 * QQ业务逻辑接口
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:15:48
 */
public interface QQ {

    /**
     * 获取qq用户信息
     * @return qq用户信息
     */
    QQUser getUser();
}
