package com.vz.paas.security.core.authentication.mobil;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信登录验证逻辑
 * 由于短信验证码的验证在过滤器已完成，直接读取用户信息即可
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 11:37:07
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    @Getter
    @Setter
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByUsername(String.valueOf(authentication.getPrincipal()));

        if (null == user) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        result.setDetails(token.getDetails());

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
