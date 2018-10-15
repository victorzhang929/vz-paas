package com.vz.paas.security.core.authentication;

import com.vz.paas.security.core.properties.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:56:43
 */
@Component
public class FormAuthenticationConfiguration {

    protected final AuthenticationSuccessHandler successHandler;

    protected final AuthenticationFailureHandler failureHandler;

    @Autowired
    public FormAuthenticationConfiguration(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage(SecurityConstant.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(successHandler)
                .failureHandler(failureHandler);

    }
}
