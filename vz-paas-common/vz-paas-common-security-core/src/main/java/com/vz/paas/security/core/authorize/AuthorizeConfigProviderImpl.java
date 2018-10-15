package com.vz.paas.security.core.authorize;

import com.vz.paas.security.core.properties.SecurityConstant;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 核心模块的授权配置提供器，安全模块涉及的url的授权配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 16:13:15
 */
@Component
@Order(Integer.MIN_VALUE)
public class AuthorizeConfigProviderImpl implements AuthorizeConfigProvider{

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(SecurityConstant.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID,
                SecurityConstant.DEFAULT_SOCIAL_USER_INFO_URL,
                SecurityConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                "/pay/alipayCallback",
                "/druid/**",
                "/auth/**",
                "/swagger-ui.html",
                "swagger-resources/**",
                "/v2/api-docs").permitAll();
        return false;
    }
}
