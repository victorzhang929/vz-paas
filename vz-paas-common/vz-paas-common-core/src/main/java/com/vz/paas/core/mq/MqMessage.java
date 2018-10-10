package com.vz.paas.core.mq;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.websocket.SendResult;

import com.google.common.base.Preconditions;
import com.vz.paas.base.exception.BusinessException;
import com.vz.paas.base.exception.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;

/**
 * 消息队列
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 20:42:33
 */
@Slf4j
@Data
@ApiModel("消息队列")
public class MqMessage implements Serializable {

    private static final long serialVersionUID = 1704722283041543256L;

    @ApiModelProperty("主题")
    private String topic;

    @ApiModelProperty("标签")
    private String tag;

    @ApiModelProperty("唯一键")
    private String key;

    @ApiModelProperty("消息体")
    private String body;

    public static Message checkMessage(MqMessage message) {
        String topic = message.getTopic();
        String key = message.getKey();
        String body = message.getBody();
        String tag = message.getTag();
        printCheckMessageLog(topic, key, body, tag);
        checkMessage(topic, key, body);
        return buildMessage(body, topic, tag, key);
    }

    public static Message checkMessag(String body, String topic, String tag, String key) {
        printCheckMessageLog(topic, key, body, tag);
        checkMessage(topic, key, body);
        return buildMessage(body, topic, tag, key);
    }

    public static Message checkMessage(Message message) {
        String body = new String(message.getBody());
        String topic = message.getTopic();
        String key = message.getKeys();
        String tag = message.getTags();
        printCheckMessageLog(topic, key, body, tag);
        checkMessage(topic, key, body);
        return buildMessage(body, topic, tag, key);
    }

    private static void checkMessage(String topic, String key, String body) {
        Preconditions.checkArgument(!StringUtils.isEmpty(topic), "发送消息失败，消息主题不能为空");
        Preconditions.checkArgument(!StringUtils.isEmpty(key), "发送消息失败，消息关键字不能为空");
        Preconditions.checkArgument(!StringUtils.isEmpty(body), "发送消息失败，消息体不能为空");
    }

    public MqMessage(Message message) {
        this.body = new String(message.getBody());
        this.topic = message.getTopic();
        this.key = message.getKeys();
        this.tag = message.getTags();
    }

    private static Message buildMessage(String body, String topic, String tag, String key) {
        Message message = new Message();
        try {
            message.setBody(body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            log.error("编码转换出现异常={}", e.getMessage(), e);
            throw new BusinessException(ErrorCodeEnum.TPC10050011);
        }
        message.setKeys(key);
        message.setTopic(topic);
        message.setTags(tag);
        return message;
    }

    private static void printCheckMessageLog(final String topic, final String key, final String body, final String tag) {
        log.info("check message - 校验MQ body={}, topic={}, tag={}, key={}", body, topic, tag, key);
    }

    /**
     * 打印生产者结果
     * @param sendResult 结果
     * @param logger 日志
     */
    public static void printProducerResult(SendResult sendResult, Logger logger) {
        if (sendResult != null) {
            logger.info("sendSimpleMessage - 发送MQ[OK]sendResult={}", sendResult);
        } else {
            logger.info("sendSimpleMessage - 发送MQ[FAIL]");
        }
    }

    /**
     * 打印生产者异常
     * @param topic 主题
     * @param tag 标签
     * @param key 唯一键
     * @param logger 日志
     * @param e 异常
     */
    public static void printProducerException(String topic, String tag, String key, Logger logger, Exception e) {
        logger.error("sendSimpleMessage - 发送MQ[FAL] topic={}, tag={}, key={}", topic, tag, key, e);
    }
}
