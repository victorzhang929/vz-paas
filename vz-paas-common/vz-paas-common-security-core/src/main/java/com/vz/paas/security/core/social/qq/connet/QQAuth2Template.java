package com.vz.paas.security.core.social.qq.connet;

import java.nio.charset.Charset;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * QQ认证模板
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:43:58
 */
@Slf4j
public class QQAuth2Template extends OAuth2Template {

    public QQAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String response = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("获取accessToken的相应={}", response);

        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(response, "&");

        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate template = super.createRestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return template;
    }
}
