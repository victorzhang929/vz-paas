package com.vz.paas.security.core.social.wechat.connet;

import lombok.Data;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 微信access_token信息
 * 不同于标准OAuth2协议，微信在获取access_token时返回openId并没有单独的通过accessToken换取openId服务
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 10:47:51
 */
@Data
public class WeChatAccessGrant extends AccessGrant {

    private static final long serialVersionUID = -7813680870625457081L;

    private String openId;

    public WeChatAccessGrant() {
        super("");
    }

    public WeChatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }
}
