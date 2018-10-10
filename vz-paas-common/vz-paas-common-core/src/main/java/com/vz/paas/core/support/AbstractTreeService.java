package com.vz.paas.core.support;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;
import com.vz.paas.base.dto.BaseTree;
import com.vz.paas.util.PublicUtil;

/**
 * 抽象树业务
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 11:41:44
 */
public abstract class AbstractTreeService<T extends BaseTree<T, ID>, ID extends Serializable> implements ITree<T, ID> {

    @Override
    public List<T> listChildTree(List<T> list, ID parentId) {
        List<T> result = Lists.newArrayList();
        for (T t : list) {
            if (t.getPid() == null) {
                continue;
            }

            if (Objects.equals(t.getPid(), parentId)) {
                recursionFn(list, t);
                result.add(t);
            }
        }
        return result;
    }

    @Override
    public void recursionFn(List<T> list, T t) {
        List<T> children = listChild(list, t);
        if (PublicUtil.isNotEmpty(children)) {
            t.setChildren(children);
            t.setHasChild(true);
        }

        for (T next : children) {
            // 下一个对象，与所有的结果集进行判断
            if (hasChild(list, next)) {
                // 有下一个子节点，递归
                for (T node : children) {
                    // 所有的对象，跟当前这个children的对象子节点
                    recursionFn(list, node);
                }
                next.setHasChild(true);
            }
        }
    }

    @Override
    public List<T> listChild(List<T> list, T t) {
        List<T> children = Lists.newArrayList();
        for (T child : list) {
            if (PublicUtil.isEmpty(child.getPid())) {
                continue;
            }

            // 判断集合的父ID是否等于上一级的ID
            if (Objects.equals(child.getPid(), t.getId())) {
                children.add(child);
            }
        }
        return children;
    }

    @Override
    public boolean hasChild(List<T> list, T t) {
        return !listChild(list, t).isEmpty();
    }
}
