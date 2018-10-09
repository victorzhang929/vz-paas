package com.vz.paas.util;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 公共工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 10:47:29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicUtil {

    /**
     * 判断对象是否Empty(null或者元素长度0)
     * 适用于如下对象判断：String、Collection、Map
     * @param obj 待检查对象
     * @return 是否为Empty
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj == "") {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).size() == 0;
        }

        return false;
    }

    /**
     * 判断对象是否为NoEmpty(!null或者元素长度大于0)
     * 适用于如下对象：String、Collection、Map
     * @param obj 待检查对象
     * @return 是否为NoEmpty
     */
    public static boolean isNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == "") {
            return false;
        }
        if (obj instanceof String) {
            return ((String) obj).length() != 0;
        } else if (obj instanceof Collection) {
            return !((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).size() != 0;
        }

        return true;
    }

    /**
     * 判断对象数组是否为NoEmpty
     * @param objs 待检查对象数组
     * @return 是否为NoEmpty
     */
    public static boolean isNotEmpty(Object[] objs) {
        boolean flag = false;

        if (null != objs && objs.length > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 生成UUID
     * @return UUID
     */
    public synchronized static String uuid() {
        return String.valueOf(UUID.randomUUID()).replace("-", "");
    }


}
