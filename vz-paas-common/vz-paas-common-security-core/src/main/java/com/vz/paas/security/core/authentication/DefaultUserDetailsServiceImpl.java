package com.vz.paas.security.core.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 默认UserDetailsService实现
 * 不做处理，只在控制台打印日志，然后抛出异常，提醒业务系统自己配置UserDetailService
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 11:08:47
 */
@Slf4j
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("请配置UserDetailsService接口实现");
        throw  new UsernameNotFoundException(username);
    }
}
