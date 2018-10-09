package com.vz.paas.util.wrapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * Mapper包装工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 14:38:54
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapMapper {

    /**
     * 包装返回结果
     * @param code 编码
     * @param message 信息
     * @param result 结果集
     * @param <T> 类型
     * @return 包装类
     */
    public static <T> Wrapper<T> wrap(int code, String message, T result) {
        return new Wrapper<>(code, message, result);
    }

    /**
     * 包装返回结果
     * @param code 编码
     * @param message 信息
     * @param <T> 类型
     * @return 包装类
     */
    public static <T> Wrapper<T> wrap(int code, String message) {
        return wrap(code, message, null);
    }

    /**
     * 包装返回结果
     * @param code 编码
     * @param <T> 类型
     * @return 包装类
     */
    public static <T> Wrapper<T> wrap(int code) {
        return wrap(code, null);
    }

    /**
     * 异常信息包装返回结果
     * @param e 异常
     * @param <T> 类型
     * @return 包装类
     */
    public static <T> Wrapper<T> wrap(Exception e) {
        return new Wrapper<>(Wrapper.ERROR_CODE, e.getMessage());
    }

    /**
     * 原数据返回
     * @param wrapper 包装类
     * @param <T> 类型
     * @return 结果集
     */
    public static <T> T unwrap(Wrapper<T> wrapper) {
        return wrapper.getResult();
    }

    /**
     * 参数非法包装返回结果
     * @param <T> 类型
     * @return 包装类
     */
    public static <T> Wrapper<T> illegalArgument() {
        return wrap(Wrapper.ILLEGAL_ARGUMENT_CODE, Wrapper.ILLEGAL_ARGUMENT_MESSAGE);
    }

    /**
     * 错误包装返回结果
     * @param <T> 类型
     * @return 包装类
     */
    public static <T> Wrapper<T> error() {
        return wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
    }

    /**
     * 错误包装返回结果
     * @param message 错误信息
     * @param <T> 类型
     * @return 包装类
     */
    public static <T> Wrapper<T> error(String message) {
        return wrap(Wrapper.ERROR_CODE, StringUtils.isBlank(message) ? Wrapper.ERROR_MESSAGE : message);
    }

    /**
     * 成功包装返回结果
     * @param <T> 类型
     * @return 包装类
     */
    public static <T> Wrapper<T> ok() {
        return new Wrapper<>();
    }

    /**
     * 成功包装返回结果
     * @param t 结果集
     * @param <T> 类型
     * @return 包装类
     */
    public static <T> Wrapper<T> ok(T t) {
        return new Wrapper<>(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, t);
    }
}
