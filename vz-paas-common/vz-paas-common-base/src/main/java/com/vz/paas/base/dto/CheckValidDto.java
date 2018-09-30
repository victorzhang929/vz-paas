package com.vz.paas.base.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 校验合法数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:32:40
 */
@Data
@ApiModel(value = "校验合法数据传输对象")
public class CheckValidDto implements Serializable {
    private static final long serialVersionUID = 8755109392056388355L;

    @ApiModelProperty(value = "校验参数值")
    private String validValue;

    @ApiModelProperty(value = "参数类型")
    private String type;
}
