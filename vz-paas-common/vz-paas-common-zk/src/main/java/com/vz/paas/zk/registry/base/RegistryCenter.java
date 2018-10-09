package com.vz.paas.zk.registry.base;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.retry.RetryNTimes;

/**
 * 注册中心
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:37:25
 */
public interface RegistryCenter {

    /**
     * 初始化注册中心
     */
    void init();

    /**
     * 关闭注册中心
     */
    void close();

    /**
     * 获取注册数据
     * @param key 键
     * @return 值
     */
    String get(String key);

    /**
     * 获取数据是否存在
     * @param key 键
     * @return 数据是否存在
     */
    boolean isExisted(String key);

    /**
     * 持久化注册数据
     * @param key 键
     * @param value 值
     */
    void persist(String key, String value);

    /**
     * 创建一个持久化节点，初始化内容为空
     * @param key 键
     */
    void persist(String key);

    /**
     * 更新注册数据
     * @param key 键
     * @param value 值
     */
    void update(String key, String value);

    /**
     * 删除注册数据
     * @param key 键
     */
    void remove(String key);

    /**
     * 获取注册中心当前时间
     * @param key 用户获取时间的键
     * @return 注册中心当前时间
     */
    long getRegistryCenterTime(String key);

    /**
     * 获取注册中心的原生客户端
     * 如：Zookeeper或Redis等原生客户端
     * @return 原生客户端
     */
    Object getRawClient();

    /**
     * 实例化实例
     * @param path 路径
     * @param retryNTimes 重试次数
     */
    void increment(String path, RetryNTimes retryNTimes);

    /**
     * 获取一个原子值
     * @param path 路径
     * @param retryNTimes 重试次数
     * @return 原子值
     */
    AtomicValue<Integer> getAtomicValue(String path, RetryNTimes retryNTimes);
}
