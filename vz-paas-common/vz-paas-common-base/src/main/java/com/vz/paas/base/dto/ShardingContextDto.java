package com.vz.paas.base.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 分片上下文数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:59:48
 */
@Data
public class ShardingContextDto implements Serializable {

    private int shardingTotalCount;

    private int shardingItem;

    public ShardingContextDto(final int shardingTotalCount, final int shardingItem) {
        this.shardingTotalCount = shardingTotalCount;
        this.shardingItem = shardingItem;
    }
}
