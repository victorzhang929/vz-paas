package com.vz.paas.feign;

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
 * 安全熔断错误中断
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 18:58:41
 */
@Slf4j
public class OAuth2FeignErrorInterceptor implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(final String methodKey, final Response response) {
        if (response.status() >= HttpStatus.BAD_REQUEST.value() && response.status() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return new HystrixBadRequestException("request exception wrapper");
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            Map map = mapper.readValue(response.body().asInputStream(), HashMap.class);
            Integer code = (Integer) map.get("code");
            String message = (String) map.get("message");

            if (code != null) {
                ErrorCodeEnum error = ErrorCodeEnum.getEnum(code);
                if (error != null) {
                    if (error == ErrorCodeEnum.GL99990100) {
                        throw new IllegalArgumentException(message);
                    } else {
                        throw new BusinessException(error);
                    }
                } else {
                    throw new BusinessException(ErrorCodeEnum.GL99990500, message);
                }
            }
        } catch (IOException ex) {
            log.info("failed to process response body");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
