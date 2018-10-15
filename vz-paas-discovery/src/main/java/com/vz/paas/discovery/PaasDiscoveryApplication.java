package com.vz.paas.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Paas服务发现应用
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 14:11:31
 */
@SpringBootApplication
@EnableConfigServer
public class PaasDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasDiscoveryApplication.class, args);
    }
}
