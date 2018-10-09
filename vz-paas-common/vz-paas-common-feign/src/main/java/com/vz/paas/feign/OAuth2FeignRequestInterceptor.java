package com.vz.paas.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.Assert;

/**
 * 安全熔断请求拦截
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 19:12:48
 */
@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private static final String BEARER_TOKEN_TYPE = "bearer";

    private final OAuth2RestTemplate template;

    public OAuth2FeignRequestInterceptor(OAuth2RestTemplate template) {
        Assert.notNull(template, "context can not be null");
        this.template = template;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.debug("constructing header {} for token {}", HttpHeaders.AUTHORIZATION, BEARER_TOKEN_TYPE);
        requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", BEARER_TOKEN_TYPE, template.getAccessToken()));
    }
}
