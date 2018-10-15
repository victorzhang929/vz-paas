package com.vz.paas.security.core.properties;

import lombok.Data;

/**
 * 邮件编码属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:16:17
 */
@Data
public class EmailCodeProperties {

    /**
     * 过期时间，默认为一天
     */
    private int expireIn = 60 * 60 * 24;

    /**
     * 拦截的url，多个url用逗号隔开
     */
    private String url;
}
