package com.vz.paas.util.page;

import lombok.Data;

/**
 * 页码工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 14:52:26
 */
@Data
public class PageUtil {

    /**
     * 当前页
     */
    private int currentPage = 1;

    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 上一页
     */
    private int prePage;

    /**
     * 总条数
     */
    private int totalRow;

    /**
     * 每页条数
     */
    private int pageSize = 10;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 开始条数
     */
    private int start;

    /**
     * 按钮
     */
    private int[] buttons;

    /**
     * 当前页条数
     */
    private int curPageSize;

    public PageUtil() {}

    public PageUtil(int currentPage) {
        this.currentPage = currentPage;
    }

    public PageUtil(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

}
