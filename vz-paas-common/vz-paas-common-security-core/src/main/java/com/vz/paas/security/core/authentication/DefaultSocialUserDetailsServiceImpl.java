package com.vz.paas.security.core.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * 默认SocialUserDetailsService实现
 * 不做处理，只在控制台打印一句日志，然后抛出异常，提醒业务系统自己配置SocialUserDetailsService
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 11:11:25
 */
@Slf4j
public class DefaultSocialUserDetailsServiceImpl implements SocialUserDetailsService {

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.warn("请配置SocialUserDetailsService接口实现");
        throw new UsernameNotFoundException(userId);
    }
}
