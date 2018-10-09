package com.vz.paas.util.wrapper;

import com.vz.paas.util.page.PageUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页码包装类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 14:50:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageWrapper<T> extends Wrapper<T> {

    private static final long serialVersionUID = -2313166606815445484L;

    private PageUtil pageUtil;

    public PageWrapper() {
        super();
    }

    public PageWrapper(int code, String message) {
        super(code, message);
    }

    public PageWrapper(T result, PageUtil pageUtil) {
        super();
        this.setResult(result);
        this.setPageUtil(pageUtil);
    }

    public PageWrapper(int code, String message, T result, PageUtil pageUtil) {
        super(code, message, result);
        this.pageUtil = pageUtil;
    }

    public PageWrapper<T> pageUtil(PageUtil pageUtil) {
        this.setPageUtil(pageUtil);
        return this;
    }

    public PageWrapper<T> result(T result) {
        super.setResult(result);
        return this;
    }
}
