package com.vz.paas.base.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 视图对象基类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:14:09
 */
@Data
@ApiModel(value = "视图对象基类")
public class BaseVo implements Serializable {

    private static final long serialVersionUID = -7732955432573561138L;

    private Long id;
    private String creator;
    private Long creatorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
    private String lastOperatorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
