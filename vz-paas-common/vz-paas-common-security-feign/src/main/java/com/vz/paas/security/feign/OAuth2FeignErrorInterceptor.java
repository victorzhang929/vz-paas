package com.vz.paas.security.feign;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.vz.paas.base.exception.BusinessException;
import com.vz.paas.base.exception.ErrorCodeEnum;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * OAuth2 Feign错误拦截器
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 11:37:32
 */
@Slf4j
public class OAuth2FeignErrorInterceptor implements ErrorDecoder {

    private static final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(final String methodKey, Response response) {
        if (response.status() >= HttpStatus.BAD_REQUEST.value() && response.status() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return new HystrixBadRequestException("request exception wrapper");
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(response.body().asInputStream(), HashMap.class);
            Integer code = (Integer) map.get("code");
            String message = (String) map.get("message");
            if (code == null) {
                ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getEnum(code);
                if (errorCodeEnum != null) {
                    if (errorCodeEnum == ErrorCodeEnum.GL99990100) {
                        throw new IllegalArgumentException(message);
                    } else {
                        throw new BusinessException(errorCodeEnum);
                    }
                } else {
                    throw new BusinessException(ErrorCodeEnum.GL99990500, message);
                }
            }
        } catch (IOException e) {
            log.error("Failed to process response body");
        }
        return errorDecoder.decode(methodKey, response);
    }
}
