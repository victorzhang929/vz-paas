package com.vz.paas.core.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vz.paas.base.constant.GlobalConstant;
import com.vz.paas.base.dto.LoginAuthDto;
import com.vz.paas.util.RedisKeyUtil;
import com.vz.paas.util.ThreadLocalMapUtil;
import com.vz.paas.util.annotation.NoNeedAccessAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Token拦截器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 18:51:05
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Value("${paas.oauth2.jwt-signing-key}")
    private String jwtSingingKey;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String OPTIONS = "OPTIONS";
    private static final String AUTH_PATH1 = "/auth";
    private static final String OAUTH_PATH = "/oauth";
    private static final String ERROR_PATH = "error";
    private static final String API_PATH = "/api";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("preHandle 权限拦截器，url={}", uri);

        if (uri.contains(AUTH_PATH1) || uri.contains(OAUTH_PATH) || uri.contains(ERROR_PATH) || uri.contains(API_PATH)) {
            log.info("preHandle 配置URL不走验证，url={}", uri);
            return true;
        }
        log.info("preHandle 调试模式不走认证，OPTIONS={}", request.getMethod().toUpperCase());

        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            log.info("preHandle 调试模式不走认证，url={}", uri);
            return true;
        }

        String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");
        log.info("preHandle 权限拦截器，token={}", token);
        if (isHaveAccess(handler)) {
            log.info("preHandle 不需要认证注解不走认证, token={}", token);
            return true;
        }

        LoginAuthDto loginUser = (LoginAuthDto) redisTemplate.opsForValue().get(RedisKeyUtil.getAccessToken(token));
        if (loginUser == null) {
            log.error("获取用户信息失败，不允许操作");
            return false;
        }
        log.info("preHandle 权限拦截器，loginUser={}", loginUser);
        ThreadLocalMapUtil.put(GlobalConstant.System.TOKEN_AUTH_DTO, loginUser);
        log.info("preHandle 权限拦截器，url={}, loginUser={}", uri, loginUser);
        return true;
    }

    /**
     * 是否具有访问权限
     * @param handler 处理类
     * @return 是否具有权限
     */
    private boolean isHaveAccess(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method = handlerMethod.getMethod();

        NoNeedAccessAuthentication response = AnnotationUtils.findAnnotation(method, NoNeedAccessAuthentication.class);
        return response != null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        if (e != null) {
            log.error("afterCompletion 解析token失败，e={}", e.getMessage(), e);
            this.handleException(response);
        }
    }

    /**
     * 处理异常信息-包装错误信息
     * @param response 响应信息
     * @throws IOException 异常
     */
    private void handleException(HttpServletResponse response) throws IOException {
        response.resetBuffer();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"code\":100009, \"message\":\"解析token失败\"}");
        response.flushBuffer();
    }
}
