package com.vz.paas.zk.registry;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.vz.paas.config.properties.AliyunProperties;
import com.vz.paas.config.properties.PaasProperties;
import com.vz.paas.config.properties.ZookeeperProperties;
import com.vz.paas.zk.generator.IncrementIdGenerator;
import com.vz.paas.zk.registry.base.CoordinatorRegistryCenter;
import com.vz.paas.zk.registry.base.RegisterDto;
import com.vz.paas.zk.registry.zookeeper.ZookeeperRegistryCenter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 注册中心工厂
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:33:24
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistryCenterFactory {

    private static final ConcurrentMap<HashCode, CoordinatorRegistryCenter> REG_CENTER_REGISTRY = new ConcurrentHashMap<>();

    public static CoordinatorRegistryCenter createCoordinatorRegistryCenter(ZookeeperProperties zookeeperProperties) {
        Hasher hasher = Hashing.md5().newHasher().putString(zookeeperProperties.getZkAddressList(), Charsets.UTF_8);
        HashCode hashCode = hasher.hash();

        CoordinatorRegistryCenter registry = REG_CENTER_REGISTRY.get(hashCode);
        if (null != registry) {
            return registry;
        }

        registry = new ZookeeperRegistryCenter(zookeeperProperties);
        registry.init();
        REG_CENTER_REGISTRY.putIfAbsent(hashCode, registry);
        return registry;
    }

    public static void startup(PaasProperties paasProperties, String host, String app) {
        CoordinatorRegistryCenter registry = createCoordinatorRegistryCenter(paasProperties.getZk());
        RegisterDto dto = new RegisterDto(app, host, registry);
        Long serviceId = new IncrementIdGenerator(dto).nextId();
        IncrementIdGenerator.setServiceId(serviceId);
        registerMq(paasProperties, host, app);
    }

    public static void registerMq(PaasProperties paasProperties, String host, String app) {
        CoordinatorRegistryCenter registry = createCoordinatorRegistryCenter(paasProperties.getZk());
        AliyunProperties.RocketMqProperties rocketMqProperties = paasProperties.getAliyun().getRocketMq();

        String consumerGroup = rocketMqProperties.isReliableMessageConsumer() ? rocketMqProperties.getConsumerGroup() : null;
        String nameSrvAddr = rocketMqProperties.getNameSrvAddr();
        String producerGroup = rocketMqProperties.isReliableMessageProducer() ? rocketMqProperties.getProducerGroup() : null;

        registry.registerMq(app, host, producerGroup, consumerGroup, nameSrvAddr);
    }
}
