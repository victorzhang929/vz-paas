package com.vz.paas.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

/**
 * Jackson工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-08 17:34:13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonUtil {

    private static ObjectMapper defaultMapper;
    private static ObjectMapper formatedMapper;

    static {
        // 默认ObjectMapper
        defaultMapper = new ObjectMapper();
        // 设置输入时忽略在JSON字符串中存在但Java中实际没有的属性
        defaultMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        formatedMapper = new ObjectMapper();
        // 设置输入时忽略在JSON字符串中存在但Java中实际没有的属性
        formatedMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置所有日期格式统一为yyyy-MM-dd HH:mm:ss
        formatedMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        formatedMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * 对象转化为json
     * @param obj 输入对象
     * @return json
     * @throws IOException IO异常
     */
    public static String toJson(Object obj) throws IOException {
        Preconditions.checkArgument(obj != null, "this argument is required; it must no be null");
        return defaultMapper.writeValueAsString(obj);
    }

    /**
     * json数据转化为对象
     * @param value json数据
     * @param type 对象类型
     * @param <T> 返回类型
     * @return 指定对象格式
     * @throws IOException IO异常
     */
    public static <T> T parseJson(String value, Class<T> type) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(value), "this argument is required; it must not be null");
        return defaultMapper.readValue(value, type);
    }

    /**
     * json数据转化为对象
     * @param value json数据
     * @param type 对象类型
     * @param <T> 返回类型
     * @return 指定对象格式
     * @throws IOException IO异常
     */
    public static <T> T parseJson(String value, JavaType type) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(value), "this argument is required, it must not be null");
        return defaultMapper.readValue(value, type);
    }

    /**
     * json数据转化为对象
     * @param value json数据
     * @param type 对象类型
     * @param <T> 返回类型
     * @return 指定对象格式
     * @throws IOException IO异常
     */
    public static <T> T parseJson(String value, TypeReference<T> type) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(value), "this argument is required, it must not be null");
        return (T) defaultMapper.readValue(value, type);
    }

    /**
     * 对象转化为特定格式化json
     * @param obj 输入对象
     * @return 格式化后json
     * @throws IOException IO异常
     */
    public static String toJsonWithFormat(Object obj) throws IOException {
        Preconditions.checkArgument(obj != null, "this argument is required, it must not be null");
        return formatedMapper.writeValueAsString(obj);
    }

    /**
     * json数据转化为格式化对象
     * @param value 格式化json数据
     * @param type 对象类型
     * @param <T> 返回类型
     * @return 指定对象格式
     * @throws IOException IO异常
     */
    public static <T> T parseJsonWithFormat(String value, Class<T> type) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(value), "this argument is required, it must not be null");
        return formatedMapper.readValue(value, type);
    }

    /**
     * json数据转化为格式化对象
     * @param value 格式化json数据
     * @param type 对象类型
     * @param <T> 返回类型
     * @return 指定对象格式
     * @throws IOException IO异常
     */
    public static <T> T parseJsonWithFormat(String value, JavaType type) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(value), "this argument is required, it must not be null");
        return formatedMapper.readValue(value, type);
    }

    /**
     * json数据转化为格式化对象
     * @param value 格式化json数据
     * @param type 对象类型
     * @param <T> 返回类型
     * @return 指定对象格式
     * @throws IOException IO异常
     */
    public static <T> T parseJsonWithFormat(String value, TypeReference<T> type) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(value), "this argument is required, it must not be null");
        return formatedMapper.readValue(value, type);
    }
}
