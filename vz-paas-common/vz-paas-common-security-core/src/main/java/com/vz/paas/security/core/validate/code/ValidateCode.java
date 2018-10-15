package com.vz.paas.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;


/**
 * 验证码封装类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 14:50:31
 */
@Data
public class ValidateCode implements Serializable {
    private static final long serialVersionUID = -3189650564419961944L;

    private String code;

    private String type;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expireTime;

    public ValidateCode() {}

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    @JsonIgnore
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
