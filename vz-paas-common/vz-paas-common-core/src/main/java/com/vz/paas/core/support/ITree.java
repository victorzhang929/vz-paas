package com.vz.paas.core.support;

import java.io.Serializable;
import java.util.List;

import com.vz.paas.base.dto.BaseTree;

/**
 * 树接口
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 11:43:09
 */
public interface ITree<T extends BaseTree<T, ID>, ID extends Serializable> {

    /**
     * 获取指定节点下所有节点归档
     * @param list 集合
     * @param parentId 父ID
     * @return 指定节点下所有节点归档
     */
    List<T> listChildTree(List<T> list, ID parentId);

    /**
     * 递归列表
     * @param list 列表
     * @param t 类型
     */
    void recursionFn(List<T> list, T t);

    /**
     * 获取指定节点下的所有子节点
     * @param list 集合
     * @param t 指定节点
     * @return 指定节点下的所有子节点
     */
    List<T> listChild(List<T> list, T t);

    /**
     * 判断是否还有下一个子节点
     * @param list 集合
     * @param t 指定节点
     * @return 是否存在下个子节点
     */
    boolean hasChild(List<T> list, T t);
}
