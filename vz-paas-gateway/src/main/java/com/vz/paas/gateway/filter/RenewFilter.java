package com.vz.paas.gateway.filter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.vz.paas.base.exception.BusinessException;
import com.vz.paas.base.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

/**
 * 网关重启拦截器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 15:52:27
 */
@Slf4j
@Component
public class RenewFilter extends ZuulFilter {

    @Resource
    private JwtTokenStore jwtTokenStore;

    private static final int EXPIRES_IN = 60 * 20;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("RenewFilter - token续租...");
        RequestContext context = RequestContext.getCurrentContext();

        try {
            doSomething(context);
        } catch (Exception e) {
            log.error("RenewFilter - token续约. [FAIL] EXCEPTION={}", e.getMessage(), e);
            throw new BusinessException(ErrorCodeEnum.UAC10011041);
        }
        return null;
    }

    private void doSomething(RequestContext context) {
        HttpServletRequest request = context.getRequest();
        String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "bearer");

        if (StringUtils.isEmpty(token)) {
            return;
        }
        OAuth2AccessToken accessToken = jwtTokenStore.readAccessToken(token);
        int expiresIn = accessToken.getExpiresIn();

        if (expiresIn < EXPIRES_IN) {
            HttpServletResponse response = context.getResponse();
            response.addHeader("Renew-Header", "true");
        }
    }
}
