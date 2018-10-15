package com.vz.paas.security.core.authentication;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * 认证相关的扩展点配置
 * 配置bean业务系统能通过声明同类型或同名bean覆盖安全
 * 模块默认配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 11:14:56
 */
@Configuration
public class AuthenticationBeanConfiguration {

    /**
     * 默认密码处理器
     * @return 密码加密器
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 默认用户详细认证器
     * @return 认证器
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsServiceImpl();
    }

    /**
     * 默认社交用户详细认证器
     * @return 认证器
     */
    @Bean
    @ConditionalOnMissingBean(SocialUserDetailsService.class)
    public SocialUserDetailsService socialUserDetailsService() {
        return new DefaultSocialUserDetailsServiceImpl();
    }
}
