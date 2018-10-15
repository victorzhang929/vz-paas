package com.vz.paas.security.core.social;

import javax.sql.DataSource;

import com.vz.paas.security.core.properties.SecurityProperties;
import com.vz.paas.security.core.social.support.PcSpringSocialConfigurer;
import com.vz.paas.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 社交登录配置主类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 16:39:01
 */
@Configuration
@EnableSocial
public class SocialConfiguration extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor processor;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("pc_uac_");
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 社交登录配置类，供浏览器或APP模块引入设计登录配置用
     * @return
     */
    @Bean
    public SpringSocialConfigurer pcSocialSecurityConfig() {
        String filterProcessUtl = securityProperties.getSocial().getFilterProcessesUtl();
        PcSpringSocialConfigurer configurer = new PcSpringSocialConfigurer(filterProcessUtl);
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        configurer.setProcessor(processor);
        return configurer;
    }

    /**
     * 处理注册流程的工具类
     * @param locator 工厂连接器
     * @return 工具类
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator locator) {
        return new ProviderSignInUtils(locator, getUsersConnectionRepository(locator));
    }
}
