package com.vz.paas.gateway;

import com.didispace.swagger.butler.EnableSwaggerButler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 网关服务
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 15:20:25
 */
@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
@EnableHystrix
@EnableSwaggerButler
public class PaasGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasGatewayApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        // 允许cookie跨域
        config.setAllowCredentials(true);
        // 允许向该服务提交请求的uri，*标识全部允许，在SpringMVC中如果设置成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
        // 允许访问的头信息，*表示全部
        config.addAllowedHeader("*");
        // 预检请求的缓存时间，秒。即在这个时间段内，对于相同的跨域请求不会再预检
        config.setMaxAge(18000L);
        // 允许提交请求的方法，*标识全部允许
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
