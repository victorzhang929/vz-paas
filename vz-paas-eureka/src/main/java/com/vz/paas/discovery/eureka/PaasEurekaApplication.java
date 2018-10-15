package com.vz.paas.discovery.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka服务中心
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 14:42:47
 */
@SpringBootApplication
@EnableEurekaServer
public class PaasEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasEurekaApplication.class, args);
    }
}
