package com.vz.paas.util.page;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 14:51:16
 */
@Data
public class Query implements Serializable {

    private static final long serialVersionUID = 5601507933664225131L;

    /**
     * 当前页
     */
    private int pageNum = 1;

    /**
     * 每页数量
     */
    private int pageSize = 20;
}
