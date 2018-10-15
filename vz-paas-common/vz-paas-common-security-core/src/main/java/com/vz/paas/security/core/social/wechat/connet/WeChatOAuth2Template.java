package com.vz.paas.security.core.social.wechat.connet;

import java.nio.charset.Charset;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 微信登录验证模板
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 18:21:01
 */
@Slf4j
public class WeChatOAuth2Template extends OAuth2Template {

    private String clientId;

    private String clientSecret;

    private String accessTokenUrl;

    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    private static final String ERR_CODE = "errcode";
    private static final String ERR_MSG = "errmsg";

    public WeChatOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid=" + clientId + "&scope=snsapi_login";
        return url;
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);

        accessTokenRequestUrl.append("?appid=").append(clientId);
        accessTokenRequestUrl.append("&secret=").append(clientSecret);
        accessTokenRequestUrl.append("&code=").append(authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri==").append(redirectUri);


        return getAccessToken(accessTokenRequestUrl);
    }

    /**
     * 获取登录凭证
     * @param accessTokenRequestUrl 登录凭证地址
     * @return 登录凭证
     */
    private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {
        log.info("获取access_token，请求URL:" + String.valueOf(accessTokenRequestUrl));
        String response = getRestTemplate().getForObject(String.valueOf(accessTokenRequestUrl), String.class);
        log.info("响应access_token，响应内容:" + response);

        Map<String, Object> result= null;
        try {
            result = new ObjectMapper().readValue(response, Map.class);
        } catch (Exception e) {
            log.error("getAccessToken={}", e.getMessage(), e);
        }

        // 返回错误码时直接返回空
        if (StringUtils.isNotBlank(MapUtils.getString(result, ERR_CODE))) {
            String errCode = MapUtils.getString(result, ERR_CODE);
            String errMsg = MapUtils.getString(result, ERR_MSG);
            throw new RuntimeException("获取access token失败，errCode:" + errCode + ",errMsg=" + errMsg);
        }

        WeChatAccessGrant grant = new WeChatAccessGrant(
                MapUtils.getString(result, "access_token"),
                MapUtils.getString(result, "scope"),
                MapUtils.getString(result, "refresh_token"),
                MapUtils.getLong(result, "expires_in")
        );

        grant.setOpenId(MapUtils.getString(result, "openid"));

        return grant;
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        StringBuilder refreshTokenUrl = new StringBuilder(REFRESH_TOKEN_URL);

        refreshTokenUrl.append("?appid=").append(clientId);
        refreshTokenUrl.append("&grant_type=refresh_token");
        refreshTokenUrl.append("&refresh_token=").append(refreshToken);

        return getAccessToken(refreshTokenUrl);
    }

    /**
     * 微信返回的contentType是html/text，添加相应的HttpMessageConverter来处理
     * @return RestTemplate
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
