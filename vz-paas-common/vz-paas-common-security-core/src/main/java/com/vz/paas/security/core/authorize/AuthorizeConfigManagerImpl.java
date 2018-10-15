package com.vz.paas.security.core.authorize;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 默认授权配置管理器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 16:19:54
 */
@Component
public class AuthorizeConfigManagerImpl implements AuthorizeConfigManager {

    private final List<AuthorizeConfigProvider> providers;

    @Autowired
    public AuthorizeConfigManagerImpl(List<AuthorizeConfigProvider> providers) {
        this.providers = providers;
    }


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        boolean existAnyRequestConfig = false;
        String existAnyRequstConfigName = null;

        for (AuthorizeConfigProvider provider : providers) {
            boolean currentIsAnyRequestConfig = provider.config(config);
            if (existAnyRequestConfig && currentIsAnyRequestConfig) {
                throw new RuntimeException("重复的anyRequest配置：" + existAnyRequstConfigName + "," + provider.getClass().getSimpleName());
            } else if (currentIsAnyRequestConfig) {
                existAnyRequestConfig = true;
                existAnyRequstConfigName = provider.getClass().getSimpleName();
            }
        }

        if (!existAnyRequestConfig) {
            config.anyRequest().authenticated();
        }
    }
}
