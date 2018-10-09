package com.vz.paas.util;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 本地线程映射工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 10:28:24
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadLocalMapUtil {

    private static final ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new MapThreadLocal();

    /**
     * 填充键值对
     * @param key 键
     * @param value 值
     */
    public static void put(String key, Object value) {
        getContextMap().put(key, value);
    }

    /**
     * 根据key移除键值对信息
     * @param key 键
     * @return 移除的键值对信息
     */
    public static Object remove(String key) {
        return getContextMap().remove(key);
    }

    /**
     * 根据键获取值信息
     * @param key 键
     * @return 对应的值
     */
    public static Object get(String key) {
        return getContextMap().get(key);
    }

    /**
     * 清理线程所有被hold住的对象，以便重用
     */
    public static void clear() {
        getContextMap().clear();
    }

    /**
     * 获取thread context map的实例
     * @return 实例信息
     */
    private static Map<String, Object> getContextMap(){
        return THREAD_CONTEXT.get();
    }

    private static class MapThreadLocal extends ThreadLocal<Map<String, Object>> {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>(8) {

                private static final long serialVersionUID = 3637958959138295593L;

                @Override
                public Object put(String key, Object value) {
                    return super.put(key, value);
                }
            };
        }
    }

}
