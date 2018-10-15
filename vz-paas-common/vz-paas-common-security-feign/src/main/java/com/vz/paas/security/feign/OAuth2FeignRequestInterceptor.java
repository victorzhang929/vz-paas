package com.vz.paas.security.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.Assert;

/**
 * OAuth2 Feign请求拦截器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 11:32:44
 */
@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private static final String BEARER_TOKEN_TYPE = "bearer";
    private final OAuth2RestTemplate oAuth2RestTemplate;

    public OAuth2FeignRequestInterceptor(OAuth2RestTemplate oAuth2RestTemplate) {
        Assert.notNull(oAuth2RestTemplate, "context can not be null");
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.debug("Constructing Header {} for token {}", HttpHeaders.AUTHORIZATION, BEARER_TOKEN_TYPE);
        requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", BEARER_TOKEN_TYPE, String.valueOf(oAuth2RestTemplate.getAccessToken())));
    }
}
