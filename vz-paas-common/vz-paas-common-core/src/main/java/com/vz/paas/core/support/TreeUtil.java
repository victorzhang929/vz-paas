package com.vz.paas.core.support;

import java.io.Serializable;

import com.vz.paas.base.dto.BaseTree;

/**
 * 数工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 11:38:14
 */
public class TreeUtil<T extends BaseTree<T, ID>, ID extends Serializable> extends AbstractTreeService<T, ID> {
}
