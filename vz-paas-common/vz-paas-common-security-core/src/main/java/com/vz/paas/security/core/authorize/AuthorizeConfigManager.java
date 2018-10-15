package com.vz.paas.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权信息管理器
 * 用于收集系统中所有AuthorizeConfigProvider并加载器配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 16:17:59
 */
public interface AuthorizeConfigManager {

    /**
     * 授权信息配置
     * @param config 配置类
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
