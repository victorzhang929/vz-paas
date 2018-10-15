package com.vz.paas.security.core.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vz.paas.security.core.properties.SecurityConstant;
import com.vz.paas.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 验证码过滤器
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 16:36:40
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证码失败处理器
     */
    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 系统中的验证码处理器
     */
    @Resource
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 存放所有需要验证码的url
     */
    private Map<String, ValidateCodeType> url = new HashMap<>();

    /**
     * 验证请求url与配置url是否匹配的工具类
     */
    private AntPathMatcher matcher = new AntPathMatcher ();

    private static final String GET = "get";

    /**
     * 初始化后拦截url配置信息
     * @throws ServletException 异常
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        url.put(SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrl(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        url.put(SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrl(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
        addUrl(securityProperties.getCode().getEmail().getUrl(), ValidateCodeType.EMAIL);
    }

    /**
     * 将系统中配置的需要验证码的url根据验证类型放入map
     * @param urlStr 验证码url
     * @param type 类型
     */
    private void addUrl(String urlStr, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlStr)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlStr, ",");
            for (String temp : urls) {
                url.put(temp, type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        ValidateCodeType type = getValidateCodeType(request);

        if (type != null) {
            logger.info("验证请求（" + request.getRequestURI() + "）中的验证码，验证码类型" + type);

            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                logger.info("验证码通过");
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * 获取验证码的类型，如果当前请求不需要验证，返回null
     * @param request 请求
     * @return 验证码类型，查找不到则为null
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType type = null;

        if (!StringUtils.equalsIgnoreCase(request.getMethod(), GET)) {
            Set<String> urls = url.keySet();
            for (String temp : urls) {
                if (matcher.match(temp, request.getRequestURI())) {
                    type = url.get(temp);
                }
            }
        }

        return type;
    }
}
