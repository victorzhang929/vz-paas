package com.vz.paas.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

/**
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 17:28:07
 */
@SpringBootApplication
@EnableZipkinServer
public class PaasZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasZipkinApplication.class, args);
    }
}
