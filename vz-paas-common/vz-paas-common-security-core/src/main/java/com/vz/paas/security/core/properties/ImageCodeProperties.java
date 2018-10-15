package com.vz.paas.security.core.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片验证码属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:17:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageCodeProperties extends SmsCodeProperties {

    /**
     * 默认图片验证码长度为4
     */
    public ImageCodeProperties() {
        super.setLength(4);
    }
}
