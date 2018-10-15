package com.vz.paas.discovery.config;

import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.sleuth.stream.StreamSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * 自定义查询器配置类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 17:29:34
 */
@Configuration
public class CustomPollerConfiguration {

    @Bean(name = StreamSpanReporter.POLLER)
    public PollerMetadata customPoller() {
        PollerMetadata poller = new PollerMetadata();
        poller.setMaxMessagesPerPoll(500);
        poller.setTrigger(new PeriodicTrigger(5000L));

        return poller;
    }

    @Bean
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }
}
