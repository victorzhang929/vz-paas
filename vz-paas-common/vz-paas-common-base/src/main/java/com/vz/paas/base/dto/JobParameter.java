package com.vz.paas.base.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务参数
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:36:58
 */
@Data
public class JobParameter implements Serializable {
    private static final long serialVersionUID = -7976468909292767406L;

    /**
     * 每次查询数据数量
     */
    private int fetchNum;

    /**
     * 取模条件
     */
    private String condition;

    /**
     * 任务类型
     */
    private String taskType;
}
