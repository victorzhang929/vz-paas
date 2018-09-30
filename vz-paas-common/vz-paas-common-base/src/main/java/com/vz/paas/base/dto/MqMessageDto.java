package com.vz.paas.base.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息队列信息数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:54:12
 */
@Data
@AllArgsConstructor
public class MqMessageDto implements Serializable {
    private static final long serialVersionUID = -6665217132540837724L;

    private String messageKey;

    private String messageTopic;
}
