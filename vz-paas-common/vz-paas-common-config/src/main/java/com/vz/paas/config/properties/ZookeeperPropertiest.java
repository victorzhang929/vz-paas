package com.vz.paas.config.properties;

import lombok.Data;

/**
 * Zookeeper配置信息
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-08 16:14:50
 */
@Data
public class ZookeeperPropertiest {

    /**
     * 连接Zookeeper服务器的列表
     * 包括IP地址和端口号
     * 多个地址用逗号分隔
     */
    private String zkAddressList;

    /**
     * Zookeeper的命名空间
     */
    private String namespace;

    /**
     * 等待重试的间隔时间初始值
     */
    private int baseSleepTimeMilliseconds = 1000;

    /**
     * 等待重试的间隔时间的最大值
     */
    private int maxSleepTimeMilliseconds = 3000;

    /**
     * 最大重试次数
     */
    private int maxRetries = 3;

    /**
     * 连接超时时间
     */
    private int connectionTimeoutMilliseconds = 15000;

    /**
     * 会话超时时间
     */
    private int sessionTimeoutMilliseconds = 60000;

    /**
     * 连接Zookeeper的权限令牌
     * 缺省为不需要权限验证
     */
    private String digest;
}
