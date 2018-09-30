package com.vz.paas.base.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 键值数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:39:03
 */
@Data
public class KvDto<K, V> implements Serializable {
    private static final long serialVersionUID = -3449196246620120111L;

    private K key;

    private V value;

    public KvDto() {}

    public KvDto(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
