package com.vz.paas.feign;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

/**
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 19:19:36
 */
@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class OAuth2FeignAutoConfiguration {

    private final OAuth2ClientProperties properties;

    @Autowired
    public OAuth2FeignAutoConfiguration(OAuth2ClientProperties properties) {
        this.properties = properties;
    }

    @Bean("paasClientCredentialsResourceDetails")
    public ClientCredentialsResourceDetails resourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId(properties.getId());
        details.setAccessTokenUri(properties.getAccessTokenUrl());
        details.setClientId(properties.getClientId());
        details.setClientSecret(properties.getClientSecret());
        details.setAuthenticationScheme(AuthenticationScheme.valueOf(properties.getClientAuthenticationScheme()));

        return details;
    }

    @Bean("paasOAuth2RestTemplate")
    public OAuth2RestTemplate oAuth2RestTemplate() {
        final OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext());
        template.setRequestFactory(new Netty4ClientHttpRequestFactory());
        return template;
    }

    @Bean
    public RequestInterceptor oAuth2FeignRequstInterceptor(@Qualifier("paasOAuth2RestTemplate") OAuth2RestTemplate template) {
        return new OAuth2FeignRequestInterceptor(template);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new OAuth2FeignErrorInterceptor();
    }


}
