package com.vz.paas.base.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 树形目录基类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:31:09
 */
@Data
public class BaseTree<E, ID> implements Serializable {

    private static final long serialVersionUID = 7814432710701882846L;

    private ID id;
    private ID pid;
    private boolean hasChild = false;
    private List<E> children;
}
