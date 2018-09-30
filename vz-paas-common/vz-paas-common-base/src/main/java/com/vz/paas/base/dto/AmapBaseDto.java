package com.vz.paas.base.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 高德地图基础数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:34:31
 */
@Data
@ApiModel(value = "高德地图基基础数据传输对象")
public class AmapBaseDto implements Serializable {
    private static final long serialVersionUID = 4725707997081746773L;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "响应信息")
    private String information;

    @ApiModelProperty(value = "响应编码")
    private String informationCode;
}
