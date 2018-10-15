package com.vz.paas.discovery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 网页安全配置类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 16:23:26
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .and()
                .logout().logoutUrl("/logout")
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**", "/applications/**", "/api/applications/**", "login.html", "/**/*.css", "/img/**", "third-party/**")
                .permitAll()
                .anyRequest().authenticated();
    }
}
