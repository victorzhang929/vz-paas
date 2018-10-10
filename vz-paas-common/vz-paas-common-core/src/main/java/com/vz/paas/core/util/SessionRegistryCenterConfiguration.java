package com.vz.paas.core.util;

import com.vz.paas.config.properties.ZookeeperProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 注册中心配置的会话声明周期
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 11:34:50
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionRegistryCenterConfiguration {

    /**
     * zk配置信息
     */
    private static ZookeeperProperties properties;

    /**
     * 从当前会话范围获取注册中心配置
     * @return 时间追踪数据源配置
     */
    public static ZookeeperProperties getRegistryCenterConfiguration() {
        return properties;
    }

    /**
     * 设置注册中心配置至当前会话范围
     * @param properties 注册中心配置
     */
    public static void setRegistryCenterConfiguration(final ZookeeperProperties properties) {
        SessionRegistryCenterConfiguration.properties = properties;
    }
}
