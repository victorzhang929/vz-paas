package com.vz.paas.base.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询基类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:29:42
 */
@Data
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 6989429137663174881L;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String orderBy;
}
