package com.vz.paas.util.help;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 长JSon序列化
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:12:29
 */
public class LongJsonSerializer extends JsonSerializer<Long> {

    /**
     * 序列化
     * @param value 待序列化数值
     * @param jsonGenerator  json生成器
     * @param serializerProvider 序列话提供器
     * @throws IOException 异常信息
     */
    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String text = (value == null ? null : String.valueOf(value));
        if (null != text) {
            jsonGenerator.writeString(text);
        }
    }
}
