package com.vz.paas.core.config;

import java.net.InetAddress;

import javax.annotation.Resource;

import com.vz.paas.config.properties.PaasProperties;
import com.vz.paas.zk.registry.RegistryCenterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Zookeeper初始启动
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 20:18:20
 */
@Slf4j
@Order
@Component
public class ZookeeperInitRunner implements CommandLineRunner {

    @Resource
    private PaasProperties properties;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void run(String... strings) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        log.info("ZookeeperInitRunner, init HostAddress={}, applicationName={}", hostAddress, applicationName);
        RegistryCenterFactory.startup(properties, hostAddress, applicationName);
        log.info("ZookeeperInitRunner, finish");
    }
}
