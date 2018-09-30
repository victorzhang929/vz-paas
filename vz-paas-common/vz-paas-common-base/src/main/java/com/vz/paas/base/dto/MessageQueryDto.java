package com.vz.paas.base.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 消息查询数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:45:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "消息查询数据传输对象")
public class MessageQueryDto extends BaseQuery {
    private static final long serialVersionUID = 1319049911389865535L;

    @ApiModelProperty(value = "消息键")
    private String messageKey;

    @ApiModelProperty(value = "消息主题")
    private String messageTopic;

    @ApiModelProperty(value = "消息标签")
    private String messageTag;

    @ApiModelProperty(value = "消息状态")
    private String messageStatus;

    @ApiModelProperty(value = "生产者组")
    private String producerGroup;

    @ApiModelProperty(value = "消息类型")
    private String messageType;

    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startQueryTime;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endQueryTime;
}
