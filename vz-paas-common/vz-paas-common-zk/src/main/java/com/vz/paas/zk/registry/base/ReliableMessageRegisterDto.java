package com.vz.paas.zk.registry.base;

import com.google.common.base.Preconditions;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 可用消息注册数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:58:50
 */
@Data
public class ReliableMessageRegisterDto {

    /**
     * 消费组
     */
    private String consumerGroup;

    /**
     * 生产组
     */
    private String producerGroup;

    /**
     * 名称服务地址
     */
    private String nameSrvAddr;

    /**
     * 设置消费组
     * @param consumerGroup 消费组
     * @return ReliableMessageRegisterDto
     */
    public ReliableMessageRegisterDto setConsumerGroup(final String consumerGroup) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(consumerGroup), "init zk consumer id is null");
        this.consumerGroup = consumerGroup;
        return this;
    }

    /**
     * 设置生产组
     * @param producerGroup 生产组
     * @return ReliableMessageRegisterDto
     */
    public ReliableMessageRegisterDto setProducerGroup(final String producerGroup) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(producerGroup), "init zk producer id is null");
        this.producerGroup = producerGroup;
        return this;
    }

    /**
     * 设置名称服务地址
     * @param nameSrvAddr 名称服务地址
     * @return ReliableMessageRegisterDto
     */
    public ReliableMessageRegisterDto setNameSrvAddr(final String nameSrvAddr) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(nameSrvAddr), "init zk nameSrvAddr is null");
        this.nameSrvAddr = nameSrvAddr;
        return this;
    }
}
