package com.vz.paas.security.core.properties;

import lombok.Data;

/**
 * Session管理属性
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 10:08:56
 */
@Data
public class SessionProperties {

    /**
     * 同一个用户在系统中的最大session数，默认为1
     */
    private int maximumSessions = 1;

    /**
     * 达到最大session时，是否阻止新的登录请求，默认为false，不阻止，新登录会将老登录失效，踢掉
     */
    private boolean maxSessionsPreventsLogin;
}
