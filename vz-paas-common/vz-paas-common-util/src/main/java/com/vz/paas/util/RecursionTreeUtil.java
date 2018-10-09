package com.vz.paas.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 递归树工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 10:42:20
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecursionTreeUtil {

    /**
     * 递归获取孩子节点信息
     * @param list 节点列表
     * @param parentId 父类ID
     * @return 孩子节点信息
     */
    public static List<TreeNode> listChildTreeNode(List<TreeNode> list, Long parentId) {
        List<TreeNode> result = new ArrayList<>();

        for (TreeNode node : list) {
            if (node.getPid() == null) {
                continue;
            }

            if (Objects.equals(node.getPid(), parentId)) {
                recursionFn(list, node);
                result.add(node);
            }
        }

        return result;
    }

    /**
     * 递归查询列表
     * @param list 列表信息
     * @param node 节点信息
     */
    private static void recursionFn(List<TreeNode> list, TreeNode node) {
        List<TreeNode> children = listChild(list, node);
        if (PublicUtil.isEmpty(children)) {
            return;
        }
        node.setChildren(children);
        for (TreeNode child : children) {
            recursionFn(list, child);
        }
    }

    /**
     * 列表孩子节点信息
     * @param list 列表信息
     * @param node 节点信息
     * @return 孩子节点信息
     */
    private static List<TreeNode> listChild(List<TreeNode> list, TreeNode node) {
        List<TreeNode> result = new ArrayList<>();

        for (TreeNode tn : list) {
            if (PublicUtil.isEmpty(tn.getPid())) {
                continue;
            }
            if (Objects.equals(tn.getPid(), node.getId())) {
                result.add(tn);
            }
        }
        return result;
    }
}
