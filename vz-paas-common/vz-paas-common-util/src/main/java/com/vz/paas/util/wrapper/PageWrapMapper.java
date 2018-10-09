package com.vz.paas.util.wrapper;

import com.vz.paas.util.page.PageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:02:03
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageWrapMapper {

    /**
     * 页码包装类返回结果
     * @param code 编码
     * @param message 信息
     * @param t 结果集
     * @param pageUtil 页码工具
     * @param <T> 类型
     * @return 页码包装类
     */
    private static <T> PageWrapper<T> wrap(int code, String message, T t, PageUtil pageUtil) {
        return new PageWrapper<>(code, message, t, pageUtil);
    }

    /**
     * 页码包装类返回结果
     * @param t 结果集
     * @param pageUtil 页码工具
     * @param <T> 类型
     * @return 成功页码包装类
     */
    public static <T> PageWrapper<T> wrap(T t, PageUtil pageUtil) {
        return wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, t, pageUtil);
    }

    /**
     * 页码包装类返回结果
     * @param code 编码
     * @param message 信息
     * @param <T> 类型
     * @return 页码包装类
     */
    public static <T> PageWrapper<T> wrap(int code, String message) {
        return wrap(code, message, null, null);
    }

    /**
     * 页码包装类返回结果
     * @param code 编码
     * @param <T> 类型
     * @return 页码包装类
     */
    public static <T> PageWrapper<T> wrap(int code) {
        return wrap(code, null, null, null);
    }

    /**
     * 页码包装类返回异常结果
     * @param e 异常
     * @param <T> 类型
     * @return 异常页码包装类
     */
    public static <T> PageWrapper<T> wrap(Exception e) {
        return new PageWrapper<>(Wrapper.ERROR_CODE, e.getMessage(), null, null);
    }

    /**
     * 原数据包装类返回结果
     * @param wrapper 页码包装类
     * @param <T> 类型
     * @return 结果集
     */
    public static <T> T unwrap(PageWrapper<T> wrapper) {
        return wrapper.getResult();
    }

    /**
     * 页码包装类返回参数非法结果
     * @param <T> 类型
     * @return 参数非法页码包装类
     */
    public static <T> PageWrapper<T> illegalArgument() {
        return wrap(Wrapper.ILLEGAL_ARGUMENT_CODE, Wrapper.ILLEGAL_ARGUMENT_MESSAGE, null, null);
    }

    /**
     * 页码包装类返回错误结果
     * @param <T> 类型
     * @return 错误页码包装类
     */
    public static <T> PageWrapper<T> error() {
        return wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE, null, null);
    }

    /**
     * 页码包装类返回成功结果
     * @param <T> 类型
     * @return 成功页码包装类
     */
    public static <T> PageWrapper<T> ok() {
        return new PageWrapper<>();
    }
}
