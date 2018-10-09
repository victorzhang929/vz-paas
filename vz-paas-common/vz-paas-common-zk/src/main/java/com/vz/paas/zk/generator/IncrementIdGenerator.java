package com.vz.paas.zk.generator;

import com.vz.paas.base.constant.GlobalConstant;
import com.vz.paas.zk.registry.base.CoordinatorRegistryCenter;
import com.vz.paas.zk.registry.base.RegisterDto;
import org.apache.curator.retry.RetryNTimes;

/**
 * FrameworkID的保存期
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:31:18
 */
public class IncrementIdGenerator implements IdGenerator {

    private static Long serviceId = null;
    private final RegisterDto registerDto;

    public IncrementIdGenerator(RegisterDto registerDto) {
        this.registerDto = registerDto;
    }

    @Override
    public Long nextId() {
        String app = this.registerDto.getApp();
        String host = this.registerDto.getHost();
        CoordinatorRegistryCenter registryCenter = this.registerDto.getCoordinatorRegistryCenter();
        String path = GlobalConstant.ZK_REGISTRY_ID_ROOT_PATH + GlobalConstant.Symbol.SLASH + app + GlobalConstant.Symbol.SLASH + host;

        if (registryCenter.isExisted(path)) {
            // 如果已经有该节点，标识已经为当前的host上部署了该APP分配的编号（应对某个服务器重启之后编号不变的问题）， 直接获取该id，而无需生成
            return Long.valueOf(registryCenter.getDirectly(GlobalConstant.ZK_REGISTRY_ID_ROOT_PATH + GlobalConstant.Symbol.SLASH + app + GlobalConstant.Symbol.SLASH + host));
        } else {
            // 节点不存在，那么需要生成ID，利用zk节点的版本号每写一次就自增的机制来实现
            registryCenter.increment(GlobalConstant.ZK_REGISTRY_SEQ, new RetryNTimes(2000, 3));
            // 生成id
            Integer id = registryCenter.getAtomicValue(GlobalConstant.ZK_REGISTRY_SEQ, new RetryNTimes(2000, 3)).postValue();
            // 将数据写入节点
            registryCenter.persist(path);
            registryCenter.persist(path, String.valueOf(id));
            return Long.valueOf(id);
        }
    }

    public static Long getServiceId() {
        return serviceId;
    }

    public static void setServiceId(Long serviceId) {
        IncrementIdGenerator.serviceId = serviceId;
    }
}
