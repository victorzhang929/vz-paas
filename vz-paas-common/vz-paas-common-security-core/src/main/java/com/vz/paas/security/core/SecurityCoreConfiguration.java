package com.vz.paas.security.core;

import com.vz.paas.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 安全核心配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:05:33
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfiguration {
}
