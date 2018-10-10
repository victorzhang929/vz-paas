package com.vz.paas.core.interceptor;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.StringUtils;

/**
 * 核心HttpRequest拦截器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 19:23:59
 */
@Slf4j
public class CoreHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestWrapper wrapper = new HttpRequestWrapper(request);

        String header = StringUtils.collectionToDelimitedString(CoreHeaderInterceptor.LABEL.get(), CoreHeaderInterceptor.HEADER_LABEL_SPLIT);
        log.info("header={}", header);
        wrapper.getHeaders().add(CoreHeaderInterceptor.HEADER_LABEL, header);
        return execution.execute(wrapper, body);
    }
}
