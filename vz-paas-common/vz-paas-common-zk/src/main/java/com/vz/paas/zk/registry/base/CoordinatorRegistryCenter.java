package com.vz.paas.zk.registry.base;

import java.util.List;

/**
 * 用于协调分布式服务的注册中心
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:37:01
 */
public interface CoordinatorRegistryCenter extends RegistryCenter {

    /**
     * 直接从注册中心而非本地缓存获取数据
     * @param key 键
     * @return 值
     */
    String getDirectly(String key);

    /**
     * 获取子节点名称集合
     * @param key 键
     * @return 子节点名称集合
     */
    List<String> listChildrenKey(String key);

    /**
     * 获取子节点数量
     * @param key 键
     * @return 子节点数量
     */
    int countChildren(String key);

    /**
     * 持久化临时注册数据
     * @param key 键
     * @param value 值
     */
    void persistEphemeral(String key, String value);

    /**
     * 持久化临时顺序注册数据
     * @param key 键
     * @param value 值
     * @return 包含10位数据的znode名称
     */
    String persistSequential(String key, String value);

    /**
     * 持久化临时顺序注册数据
     * @param key 键
     */
    void persistEphemeralSequential(String key);

    /**
     * 添加本地缓存
     * @param cachePath 需加入缓存的路径
     */
    void addCacheDate(String cachePath);

    /**
     * 释放本地缓存
     * @param cachePath 需释放缓存的路径
     */
    void evictCacheData(String cachePath);

    /**
     * 获取注册中心数据缓存对象
     * @param cachePath 缓存路径
     * @return 注册中心数据缓存对象
     */
    Object getRawCache(String cachePath);

    /**
     * 想注册中心进行注册，生成该服务的编号并返回
     * @param app 应用
     * @param host 端口
     * @param producerGroup 生产者组
     * @param consumerGroup 消费者组
     * @param nameSrvAddr 名称服务地址
     */
    void registerMq(String app, String host, String producerGroup, String consumerGroup, String nameSrvAddr);
}
