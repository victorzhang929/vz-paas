package com.vz.paas.config.properties;

import lombok.Data;

/**
 * 阿里云配置信息
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-08 16:42:05
 */
@Data
public class AliyunProperties {

    private AliyunKeyProperties key = new AliyunKeyProperties();

    private RocketMqProperties rocketMq = new RocketMqProperties();

    private AliyunSmsProperties sms = new AliyunSmsProperties();

    @Data
    public class AliyunKeyProperties {

        /**
         * 密钥ID
         */
        private String accessKeyId;

        /**
         * 密钥
         */
        private String accessKeySecret;
    }

    @Data
    public class RocketMqProperties {
        /**
         * 消费者组
         */
        private String consumerGroup;

        /**
         * 生产者组
         */
        private String producerGroup;

        /**
         * 服务地址
         */
        private String nameSrvAddr;

        /**
         * 生产者是否使用可靠消息，默认不使用@MqConsumerStore
         */
        private boolean reliableMessageProducer;

        /**
         * 消费者是否使用可靠消息，默认不实用@MqProducerStore
         */
        private boolean reliableMessageConsumer;
    }

    @Data
    public class AliyunSmsProperties {
        /**
         * 阿里云管理控制台配置的短信签名（状态必须是验证通过）
         */
        private String signName;

        /**
         * 机房信息
         */
        private String regionId;

        /**
         * 端点
         */
        private String endpoint;

        /**
         * 端点名称
         */
        private String endpointName;

        /**
         * 产品
         */
        private String product;

        /**
         * 范围
         */
        private String domain;
    }
}
