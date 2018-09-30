package com.vz.paas.base.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息队列信息视图对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:55:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MqMessageVo extends BaseVo {
    private static final long serialVersionUID = -3317790924247599487L;

    /**
     * 消息键
     */
    private String messageKey;

    /**
     * 消息主题
     */
    private String messageTopic;

    /**
     * 消息标签
     */
    private String messageTag;

    /**
     * 消息体
     */
    private String messageBody;

    /**
     * 消息类型：10生产者；20消费者
     */
    private Integer messageType;

    /**
     * 顺序类型：0有序；1无序
     */
    private int orderType;

    /**
     * 消息状态
     */
    private Integer status;

    /**
     * 延时级别
     */
    private int delayLevel;
}
