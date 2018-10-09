package com.vz.paas.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Url工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 10:07:17
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlUtil {

    private final static String ENCODE = "GBK";

    /**
     * URL解码
     * @param url 地址
     * @return 解码后URL地址
     */
    public static String getURLDecoderString(String url) {
        if (null == url) {
            return "";
        }

        String result = "";
        try {
            result = URLDecoder.decode(url, ENCODE);
        } catch (UnsupportedEncodingException e) {
            log.error("URL解析失败，e={}", e.getMessage(), e);
        }

        return result;
    }

    /**
     * URL转码
     * @param url 地址
     * @return 转码后URL
     */
    public static String getURLEncodeString(String url) {
        if (null == url) {
            return "";
        }

        String result = "";
        try {
            result = URLEncoder.encode(url, ENCODE);
        } catch (UnsupportedEncodingException e) {
            log.error("URL转码失败，e={}", e.getMessage(), e);
        }

        return result;
    }
}
