package com.vz.paas.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Charsets;
import com.vz.paas.base.constant.GlobalConstant;
import com.vz.paas.base.dto.LoginAuthDto;
import com.vz.paas.base.exception.BusinessException;
import com.vz.paas.base.exception.ErrorCodeEnum;
import com.vz.paas.util.PublicUtil;
import com.vz.paas.util.ThreadLocalMapUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 请求工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 10:54:19
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtil {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取远端地址
     * @param request 请求
     * @return 远端地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader(GlobalConstant.X_REAL_IP);
        if (StringUtils.isEmpty(ip) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(GlobalConstant.X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ip) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(GlobalConstant.PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ip) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(GlobalConstant.WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ip) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(GlobalConstant.HTTP_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ip) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(GlobalConstant.HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ip) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isEmpty(ip) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (GlobalConstant.LOCALHOST_IP.equals(ip) || GlobalConstant.LOCALHOST_IP_16.equals(ip)) {
                // 根据网卡获取本机配置的ip
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("获取IP地址出现异常={}", e.getMessage(), e);
                }
                assert inet != null;
                ip = inet.getHostAddress();
            }
            log.info("获取IP地址，ip={}", ip);
        }
        // 对于通过多个代理的情况，第一个ip为客户端真实ip，多个ip按照“,”分割
        if (ip != null && ip.length() > GlobalConstant.MAX_IP_LENGTH) {
            if (ip.indexOf(GlobalConstant.Symbol.COMMA) > 0) {
                ip = ip.substring(0, ip.indexOf(GlobalConstant.Symbol.COMMA));
            }
        }
        return ip;
    }

    /**
     * 获取登录用户
     * @return 登录用户信息
     */
    public static LoginAuthDto getLoginUser() {
        LoginAuthDto dto = (LoginAuthDto) ThreadLocalMapUtil.get(GlobalConstant.System.TOKEN_AUTH_DTO);
        if (PublicUtil.isEmpty(dto)) {
            throw new BusinessException(ErrorCodeEnum.UAC10011039);
        }
        return dto;
    }

    /**
     * 获取验证头部
     * @param request 请求
     * @return 验证信息
     */
    public static String getAuthHeader(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(header)) {
            throw new BusinessException(ErrorCodeEnum.UAC10011040);
        }
        return header;
    }

    /**
     * 分解解压头部信息
     * @param header 头部信息
     * @return 分解解压后信息
     */
    public static String[] extractAndDecodeHeader(String header) {
        byte[] base64Token = header.substring(6).getBytes(Charsets.UTF_8);
        byte[] decoded;

        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("failed to decode basic authentication token");
        }

        String token = new String(decoded, Charsets.UTF_8);
        int delim = token.indexOf(GlobalConstant.Symbol.COLON);

        if (delim == -1) {
            throw new BadCredentialsException("invalid basic authentication token");
        }

        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}
