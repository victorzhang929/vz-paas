package com.vz.paas.security.core.validate.code.impl;

import java.util.concurrent.TimeUnit;

import com.vz.paas.security.core.validate.code.ValidateCode;
import com.vz.paas.security.core.validate.code.ValidateCodeException;
import com.vz.paas.security.core.validate.code.ValidateCodeRepository;
import com.vz.paas.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 10:23:23
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisValidateCodeRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        String key = buildKey(request, validateCodeType);
        redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, validateCodeType));
        if (null == value) {
            return null;
        }
        return (ValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        redisTemplate.delete(buildKey(request, validateCodeType));
    }

    /**
     * 构建redis的key
     * @param request 请求
     * @param type 类型
     * @return key
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }

        return "code:" + String.valueOf(type).toLowerCase() + ":" + deviceId;
    }
}
