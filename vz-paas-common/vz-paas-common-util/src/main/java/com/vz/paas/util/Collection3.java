package com.vz.paas.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Collection工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 14:12:21
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Collection3 {

    /**
     * 并集
     * @param one 其一collection及其子类
     * @param another 其一collection及其子类
     * @param <T> 类型
     * @return 并集集合
     */
    public static <T> List<T> union(final Collection<T> one, final Collection<T> another) {
        List<T> result = new ArrayList<>(one);
        result.addAll(another);
        return result;
    }

    /**
     * 去除one集合中包含another集合的元素
     * @param one 其一collection及其子类
     * @param another 其一collection及其子类
     * @param <T> 类型
     * @return one集合去除another集合元素后的集合
     */
    public static <T> List<T> substract(final Collection<T> one, final Collection<T> another) {
        List<T> result = new ArrayList<>(one);
        for (T element : another){
            result.remove(element);
        }
        return result;
    }

    /**
     * 并集
     * @param one 其一collection及其子类
     * @param another 其一collection及其子类
     * @param <T> 类型
     * @return 并集集合
     */
    public static <T> List<T> intersection(Collection<T> one, Collection<T> another) {
        List<T> result = new ArrayList<>();
        for (T element : one) {
            if (another.contains(element)) {
                result.add(element);
            }
        }

        return result;
    }
}
