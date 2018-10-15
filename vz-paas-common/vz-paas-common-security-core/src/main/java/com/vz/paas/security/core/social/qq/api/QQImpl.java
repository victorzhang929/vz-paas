package com.vz.paas.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * QQ实现类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:27:51
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String URL_GET_USER = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private String appId;
    private String openId;
    private ObjectMapper mapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        log.info("result={}", result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }


    @Override
    public QQUser getUser() {

        String url = String.format(URL_GET_USER, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        log.info("result={}", result);
        QQUser user;

        try {
            user = mapper.readValue(result, QQUser.class);
            user.setOpenId(openId);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
