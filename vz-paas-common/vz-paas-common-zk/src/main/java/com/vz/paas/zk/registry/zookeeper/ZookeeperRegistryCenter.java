package com.vz.paas.zk.registry.zookeeper;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.vz.paas.base.constant.GlobalConstant;
import com.vz.paas.config.properties.ZookeeperProperties;
import com.vz.paas.zk.registry.base.CoordinatorRegistryCenter;
import com.vz.paas.zk.registry.base.ReliableMessageRegisterDto;
import com.vz.paas.zk.registry.exception.RegExceptionHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

/**
 * 基于Zookeeper的注册中心
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 16:15:43
 */
@Slf4j
public class ZookeeperRegistryCenter implements CoordinatorRegistryCenter {

    /**
     * zookeeper配置
     */
    @Getter(AccessLevel.PROTECTED)
    private ZookeeperProperties zkConfig;

    /**
     * 客户端
     */
    @Getter
    private CuratorFramework client;

    /**
     * 分布式原子Integer
     */
    @Getter
    private DistributedAtomicInteger distributedAtomicInteger;

    private final Map<String, TreeCache> caches = new HashMap<>();

    public ZookeeperRegistryCenter(final ZookeeperProperties zkConfig) {
        this.zkConfig = zkConfig;
    }

    @Override
    public void init() {
        log.debug("Elastic job: zookeeper registry center init, server list is: {}.", zkConfig.getZkAddressList());
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(zkConfig.getZkAddressList())
                .retryPolicy(new ExponentialBackoffRetry(zkConfig.getBaseSleepTimeMilliseconds(), zkConfig.getMaxRetries(), zkConfig.getMaxSleepTimeMilliseconds()));
        if (0 != zkConfig.getSessionTimeoutMilliseconds()) {
            builder.sessionTimeoutMs(zkConfig.getSessionTimeoutMilliseconds());
        }
        if (0 != zkConfig.getConnectionTimeoutMilliseconds()) {
            builder.connectionTimeoutMs(zkConfig.getConnectionTimeoutMilliseconds());
        }
        if (!Strings.isNullOrEmpty(zkConfig.getDigest())) {
            builder.authorization("digest", zkConfig.getDigest().getBytes(Charsets.UTF_8))
                    .aclProvider(new ACLProvider() {
                        @Override
                        public List<ACL> getDefaultAcl() {
                            return ZooDefs.Ids.CREATOR_ALL_ACL;
                        }

                        @Override
                        public List<ACL> getAclForPath(String s) {
                            return ZooDefs.Ids.CREATOR_ALL_ACL;
                        }
                    });
        }
        client = builder.build();
        client.start();

        try {
            if (!client.blockUntilConnected(zkConfig.getMaxSleepTimeMilliseconds() * zkConfig.getMaxRetries(), TimeUnit.MILLISECONDS)) {
                client.close();
                throw new KeeperException.OperationTimeoutException();
            }
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }
    }

    @Override
    public void close() {
        for (Map.Entry<String, TreeCache> item : caches.entrySet()) {
            item.getValue().close();
        }
        waitForCacheClose();
        CloseableUtils.closeQuietly(client);
    }

    private void waitForCacheClose() {
        try {
            Thread.sleep(500L);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String get(final String key) {
        TreeCache cache = findTreeCache(key);
        if (null == cache) {
            return getDirectly(key);
        }
        ChildData resultInCache = cache.getCurrentData(key);
        if (null != resultInCache) {
            return null == resultInCache.getData() ? null : new String(resultInCache.getData(), Charsets.UTF_8);
        }
        return getDirectly(key);
    }

    private TreeCache findTreeCache(final String key) {
        for (Map.Entry<String, TreeCache> entry : caches.entrySet()) {
            if (key.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public String getDirectly(final String key) {
        try {
            return new String(client.getData().forPath(key), Charsets.UTF_8);
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
            return null;
        }
    }

    @Override
    public List<String> listChildrenKey(final String key) {
        try {
            List<String> result = client.getChildren().forPath(key);
            result.sort(Comparator.reverseOrder());
            return result;
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
            return Collections.emptyList();
        }
    }

    @Override
    public int countChildren(final String key) {
        Stat stat = null;
        try {
            stat = client.checkExists().forPath(key);
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }
        return stat == null ? 0 : stat.getNumChildren();
    }

    @Override
    public boolean isExisted(final String key) {
        try {
            return null != client.checkExists().forPath(key);
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
            return false;
        }
    }

    @Override
    public void persist(final String key, final String value) {
        try {
            if (!isExisted(key)) {
                if (null == value) {
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(key);
                } else {
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(key, value.getBytes(Charsets.UTF_8));
                }
            } else {
                if (null != value) {
                    update(key, value);
                }
            }
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }
    }

    @Override
    public void persist(final String key) {
        this.persist(key, null);
    }

    @Override
    public void update(final String key, final String value) {
        try {
            client.inTransaction().check().forPath(key).and().setData().forPath(key, value.getBytes(Charsets.UTF_8)).and().commit();
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }
    }

    @Override
    public void persistEphemeral(final String key, final String value) {
        try {
            if (isExisted(key)) {
                client.delete().deletingChildrenIfNeeded().forPath(key);
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key, value.getBytes(Charsets.UTF_8));
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }
    }

    @Override
    public String persistSequential(final String key, final String value) {
        try {
            return client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(key, value.getBytes(Charsets.UTF_8));
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }
        return null;
    }

    @Override
    public void persistEphemeralSequential(final String key) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(key);
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }
    }

    @Override
    public void remove(final String key) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(key);
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }
    }

    @Override
    public long getRegistryCenterTime(final String key) {
        long result = 0L;
        try {
            persist(key, "");
            result = client.checkExists().forPath(key).getMtime();
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }

        Preconditions.checkState(0L != result, "can not get registry center time");
        return result;
    }

    @Override
    public Object getRawClient() {
        return client;
    }

    @Override
    public void increment(final String path, final RetryNTimes retryNTimes) {
        try {
            distributedAtomicInteger = new DistributedAtomicInteger(client, path, retryNTimes);
            distributedAtomicInteger.increment();
        } catch (final Exception ex) {
            log.error("increment={}", ex.getMessage(), ex);
        }
    }

    @Override
    public AtomicValue<Integer> getAtomicValue(final String path, final RetryNTimes retryNTimes) {
        try {
            distributedAtomicInteger = new DistributedAtomicInteger(client, path, retryNTimes);
            return distributedAtomicInteger.get();
        } catch (final Exception ex) {
            log.error("getAtomicValue = {}", ex.getMessage(), ex);
        }

        return null;
    }

    @Override
    public void addCacheDate(final String cachePath) {
        TreeCache cache = new TreeCache(client, cachePath);
        try {
            cache.start();
        } catch (final Exception ex) {
            RegExceptionHandler.handleException(ex);
        }

        caches.put(cachePath + GlobalConstant.Symbol.SLASH , cache);
    }

    @Override
    public void evictCacheData(final String cachePath) {
        TreeCache cache = caches.remove(cachePath + GlobalConstant.Symbol.SLASH );
        if (null != cache) {
            cache.close();
        }
    }

    @Override
    public Object getRawCache(final String cachePath) {
        return caches.get(cachePath + GlobalConstant.Symbol.SLASH );
    }

    @Override
    public void registerMq(final String app, final String host, final String producerGroup, final String consumerGroup, final String nameSrvAddr) {
        // 注册生产者
        final String producerRootPath = GlobalConstant.ZK_REGISTRY_PRODUCER_ROOT_PATH + GlobalConstant.Symbol.SLASH + app;
        // 注册消费者
        final String consumerRootPath = GlobalConstant.ZK_REGISTRY_CONSUMER_ROOT_PATH + GlobalConstant.Symbol.SLASH + app;

        ReliableMessageRegisterDto dto;
        if (StringUtils.isNotEmpty(producerGroup)) {
            dto = new ReliableMessageRegisterDto().setProducerGroup(producerGroup).setNameSrvAddr(nameSrvAddr);
            String producerJson = JSON.toJSONString(dto);
            this.persist(producerRootPath, producerJson);
            this.persistEphemeral(producerRootPath + GlobalConstant.Symbol.SLASH + host, String.valueOf(System.currentTimeMillis()));
        }
        if (StringUtils.isNotEmpty(consumerRootPath)) {
            dto = new ReliableMessageRegisterDto().setConsumerGroup(consumerRootPath).setNameSrvAddr(nameSrvAddr);
            String consumerJson = JSON.toJSONString(dto);
            this.persist(consumerRootPath, consumerJson);
            this.persistEphemeral(consumerRootPath + GlobalConstant.Symbol.SLASH + host, String.valueOf(System.currentTimeMillis()));
        }
    }
}
