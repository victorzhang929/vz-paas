package com.vz.paas.discovery;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ListConfig;
import com.hazelcast.config.MapConfig;
import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;

/**
 * 模拟器服务
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 16:27:21
 */
@SpringBootApplication
@EnableTurbine
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableAdminServer
public class PaasMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasMonitorApplication.class, args);
    }

    @Bean
    public Config hazelcastConfig() {
        return new Config().setProperty("hazelcast.jmx", "true")
                .addMapConfig(new MapConfig("spring-boot-admin-application-store").setBackupCount(1)
                        .setEvictionPolicy(EvictionPolicy.NONE))
                .addListConfig(new ListConfig("spring-boot-admin-event-store").setBackupCount(1)
                        .setMaxSize(1000));
    }
}
