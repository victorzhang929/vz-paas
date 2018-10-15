package com.vz.paas.security.core.social.wechat.api;

import java.nio.charset.Charset;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 微信api调用模板，scope为request的spring bean
 * 根据当前用户的accessToken创建
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 18:11:30
 */
@Slf4j
public class WeChatImpl extends AbstractOAuth2ApiBinding implements WeChat {

    private ObjectMapper mapper = new ObjectMapper();
    private static final String ERR_CODE = "errcode";

    private static final String URL_GET_USER = "https://api.weixin.qq.com.sns.userinfo?openid=";

    public WeChatImpl(String token) {
        super(token, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = super.getMessageConverters();
        converters.remove(0);
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return converters;
    }

    @Override
    public WeChatUser getUser(String openId) {
        String url = URL_GET_USER + openId;
        String response = getRestTemplate().getForObject(url, String.class);
        if (StringUtils.contains(response, ERR_CODE)) {
            return null;
        }

        WeChatUser user = null;
        try {
            user = mapper.readValue(response, WeChatUser.class);
        } catch (Exception e) {
            log.error("getUser={}", e.getMessage(), e);
        }
        return user;
    }
}
