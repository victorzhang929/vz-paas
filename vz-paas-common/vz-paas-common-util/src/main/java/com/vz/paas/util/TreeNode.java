package com.vz.paas.util;

import java.util.List;

import lombok.Data;

/**
 * 树节点工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 10:25:07
 */
@Data
public class TreeNode {

    /**
     * 节点编码
     */
    private String nodeCode;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * ID
     */
    private Long id;

    /**
     * 父ID
     */
    private Long pid;

    /**
     * 孩子节点信息
     */
    private List<TreeNode> children;
}
