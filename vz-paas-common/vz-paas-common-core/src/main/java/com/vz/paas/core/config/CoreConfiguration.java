package com.vz.paas.core.config;

import com.vz.paas.core.interceptor.SqlLogInterceptor;
import com.vz.paas.core.interceptor.TokenInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 核心配置信息，LWR规则
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 17:14:10
 */
@Configuration
public class CoreConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SqlLogInterceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(HandlerInterceptor.class)
    @ConditionalOnProperty(prefix = "paas.token.interceptor", name = "enable", havingValue = "true")
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }
}
