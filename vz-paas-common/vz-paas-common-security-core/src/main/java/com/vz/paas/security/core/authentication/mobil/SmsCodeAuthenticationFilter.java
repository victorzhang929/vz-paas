package com.vz.paas.security.core.authentication.mobil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vz.paas.security.core.properties.SecurityConstant;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

/**
 * 短信登录过滤器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 11:20:46
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String POST = "POST";

    private String mobileParameter = SecurityConstant.DEFAULT_PARAMETER_NAME_MOBILE;
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, "post"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("authentication method not support: " + request.getMethod());
        }

        String mobile = obtainMobile(request);
        if (null == mobile) {
            mobile = "";
        }
        mobile = mobile.trim();

        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(mobile);
        setDetails(request, token);

        return this.getAuthenticationManager().authenticate(token);
    }

    /**
     * 获取手机号
     * @param request 请求
     * @return 手机号码
     */
    protected  String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken token) {
        token.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "username parameter must not be empty or null");
        this.mobileParameter = usernameParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }
}
