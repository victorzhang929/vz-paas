package com.vz.paas.security.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * SocialAuthenticationFilter后置处理器，用在不同环境下个性化社交登录的配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 16:31:38
 */
public interface SocialAuthenticationFilterPostProcessor {

    /**
     * 处理过程
     * @param filter 拦截器
     */
    void process(SocialAuthenticationFilter filter);
}
