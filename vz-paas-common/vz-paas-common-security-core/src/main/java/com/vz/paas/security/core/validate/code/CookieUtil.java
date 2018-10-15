package com.vz.paas.security.core.validate.code;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Cookie工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 14:33:24
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class CookieUtil {

    /**
     * 设置cookie域，默认为paas.net
     */
    private static final String COOKIE_DOMAIN = "paas.net";

    /**
     * 设置默认路径：/，这个路径即该工程下都可以访问该cookie
     * 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
     */
    private static final String COOKIE_PATH = "/";

    /**
     * 设置cookie有效期，根据需要自定义
     * 默认为一周，7天
     */
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 7;

    /**
     * 设置Cookie
     * @param name 名称
     * @param value 值
     * @param maxAge 最大有效期
     * @param response 响应
     */
    public static void setCookie(String name, String value, Integer maxAge, HttpServletResponse response) {
        log.info("setCookie - 设置cookie，name={}, value={}, maxAge={}", name, value, maxAge);
        Cookie cookie;

        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("cookie转码异常");
        }

        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(maxAge == null ? COOKIE_MAX_AGE : maxAge);
        response.addCookie(cookie);

        log.info("setCookie - 设置cookie. [ok]");
    }

    /**
     * 根据Cookie的名称得到Cookie的值
     * @param request 请求
     * @param name 名称
     * @return cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 根据Cookie名称得到Cookie对象
     * @param request 请求
     * @param name 名称
     * @return cookie对象
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        log.info("获取指定名称cookie，name={}", name);

        Cookie[] cookies = request.getCookies();
        if (cookies == null || StringUtils.isBlank(name)) {
            return null;
        }

        Cookie cookie = null;
        for (Cookie temp : cookies) {
            if (!temp.getName().equals(name) || StringUtils.isBlank(temp.getDomain())) {
                continue;
            }
            cookie = temp;
            if (request.getServerName().contains(cookie.getDomain())) {
                break;
            }
        }

        return cookie;
    }

    /**
     * 删除指定名称cookie
     * @param name 名称
     * @param response 响应
     */
    public static void removeCookie(String name, HttpServletResponse response) {
        log.info("removeCookie - 删除指定名称的Cookie，key={}", name);

        Cookie cookie = new Cookie(name, null);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        log.info("removeCookie - 删除指定名称的Cookie. [ok]");
    }
}
