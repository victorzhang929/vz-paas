package com.vz.paas.base.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 全局常量
 * @author zhangwei
 * @date 29/09/2018 17:37
 * @email victorzhang0929@hotmail.com
 */
public class GlobalConstant {

    public static final long FILE_MAX_SIZE = 5 * 1024 * 1024;
    public static final String UNKNOWN = "unknown";

    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_REAL_IP = "X-Real-IP";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    public static final String LOCALHOST_IP = "127.0.0.1";
    public static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
    public static final int MAX_IP_LENGTH = 15;

    public static final String DEV_PROFILE = "dev";
    public static final String TEST_PROFILE = "test";
    public static final String PROD_PROFILE = "prod";

    public static final int TWO_INT = 2;
    public static final int M_SIZE = 1024;
    public static final String ROOT_PREFIX = "paas";

    public static final int EXCEPTION_CAUSE_MAX_LENGTH = 2048;
    public static final int EXCEPTION_MESSAGE_MAX_LENGTH = 2048;

    public static final String ZK_REGISTRY_SERVICE_ROOT_PATH = "/paas/registry/service";
    public static final String ZK_REGISTRY_ID_ROOT_PATH = "/paas/registry/id";
    public static final String ZK_REGISTRY_PRODUCER_ROOT_PATH = "/paas/registry/producer";
    public static final String ZK_REGISTRY_CONSUMER_ROOT_PATH = "/paas/registry/consumer";
    public static final String ZK_REGISTRY_SEQ = "/paas/seq";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Symbol {

        public static final String COMMA = ",";
        public static final String SPOT = ".";
        public static final String UNDER_LINE = "_";
        public static final String PERCENT = "%";
        public static final String AT = "@";
        public static final String PIPE = "||";
        public static final String SHORT_LINE = "-";
        public static final String SPACE = " ";
        public static final String SLASH = "/";
        public static final String COLON = ":";

    }
    
}
