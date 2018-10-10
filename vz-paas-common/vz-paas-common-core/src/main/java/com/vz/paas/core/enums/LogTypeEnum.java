package com.vz.paas.core.enums;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;

/**
 * 日志类型枚举
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 15:38:04
 */
public enum LogTypeEnum {

    /**
     * 操作日志
     */
    OPERATION_LOG("10", "操作日志"),

    /**
     * 登录日志
     */
    LOGIN_LOG("20", "登录日志"),

    /**
     * 异常日志
     */
    EXEPTION_LOG("30", "异常日志");

    /**
     * 类型
     */
    @Getter
    private String type;

    /**
     * 名称
     */
    @Getter
    private String name;

    LogTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * 根据类型获取名称
     * @param type 类型
     * @return 名称
     */
    public static String getName(String type) {
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele.getName();
            }
        }
        return null;
    }

    /**
     * 根据类型获取枚举实体信息
     * @param type 类型
     * @return 枚举实体，默认为OPERATION_LOG
     */
    public static LogTypeEnum getEnum(String type) {
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele;
            }
        }
        return LogTypeEnum.OPERATION_LOG;
    }

    /**
     * 列举所有枚举集合
     * @return 枚举集合（key--type, value--name）
     */
    public static List<Map<String, Object>> list() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", ele.getType());
            map.put("value", ele.getName());
            list.add(map);
        }
        return list;
    }
}
