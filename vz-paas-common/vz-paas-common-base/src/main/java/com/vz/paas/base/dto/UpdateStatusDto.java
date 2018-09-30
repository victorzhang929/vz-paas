package com.vz.paas.base.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新状态数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 15:02:06
 */
@Data
@ApiModel(value = "更改状态")
public class UpdateStatusDto implements Serializable {

    private static final long serialVersionUID = -5790692345601359075L;

    @ApiModelProperty(value = "角色ID")
    private Long id;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
