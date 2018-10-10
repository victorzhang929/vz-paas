package com.vz.paas.core.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 令牌缓存类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 10:41:40
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenCache {

    private static final String NULL = "null";

    /**
     * LRU算法
     */
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000).maximumSize(1000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) {
                    return NULL;
                }
            });

    /**
     * 设置缓存键值对
     * @param key 键
     * @param value 值
     */
    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    /**
     * 根据key获取缓存value
     * @param key 键
     * @return 值
     */
    public static String getKey(String key) {
        try {
            String value = localCache.get(key);
            if (NULL.equals(value)) {
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            log.error("localCache get error", e);
        }
        return null;
    }

}
