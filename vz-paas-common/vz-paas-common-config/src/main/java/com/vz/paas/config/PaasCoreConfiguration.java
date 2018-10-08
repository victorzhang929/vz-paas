package com.vz.paas.config;

import com.vz.paas.config.properties.PaasProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 核心配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-08 16:13:10
 */
@Configuration
@EnableConfigurationProperties(PaasProperties.class)
public class PaasCoreConfiguration {
}
