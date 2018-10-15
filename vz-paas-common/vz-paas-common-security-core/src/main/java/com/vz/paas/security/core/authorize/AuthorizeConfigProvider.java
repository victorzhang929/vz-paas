package com.vz.paas.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权配置提供器，各个模块和业务系统可以通过实现此接口向系统添加授权配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 11:48:48
 */
public interface AuthorizeConfigProvider {

    /**
     * 配置服务
     * @param config 配置类
     * @return 是否成功
     */
    boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
