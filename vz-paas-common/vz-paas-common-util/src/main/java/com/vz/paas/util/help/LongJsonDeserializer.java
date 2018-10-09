package com.vz.paas.util.help;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:16:06
 */
@Slf4j
public class LongJsonDeserializer extends JsonDeserializer<Long> {

    /**
     * 反序列化
     * @param jsonParser json修饰器
     * @param deserializationContext 反序列化上下文
     * @return 反序列化值
     */
    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        String value = null;
        try {
            value = jsonParser.getText();
        } catch (IOException e) {
            log.error("deserialize={}", e.getMessage(), e);
        }

        try {
            return value == null ? null : Long.parseLong(value);
        } catch (NumberFormatException e) {
            log.error("解析长整型错误", e);
            return null;
        }
    }
}
