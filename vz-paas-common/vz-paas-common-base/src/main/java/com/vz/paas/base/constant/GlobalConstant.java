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
    public static final String USER_AGENT = "User-Agent";

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

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class System {
        /**
         * 全局用户名
         */
        public static final String TOKEN_AUTH_DTO = "CURRENT_USER_DTO";

        /**
         * 超级管理员的用户ID
         */
        public static final Long SUPER_MANAGER_USER_ID = 1L;

        /**
         * 超级管理员的用户编号
         */
        public static final String SUPER_MANAGER_LOGIN_NAME = "admin";

        /**
         * 超级管理员的角色ID
         */
        public static final Long SUPER_MANAGER_ROLE_ID = 1L;

        /**
         * 超级管理员的组织ID
         */
        public static final Long SUPER_MANAGER_GROUP_ID = 1L;

        /**
         * 运营工作天ID
         */
        public static final Long OPER_APPLICATION_ID = 1L;

        /**
         * 基础菜单
         */
        public static final String MENU_ROOT = "root";

        /**
         * 默认文件存放地址
         */
        public static final String DEFAULT_FILE_PATH = "paas/file/";

        /**
         * 默认redis失效时间
         */
        public static final Long REDIS_DEFAULT_EXPIRE = 1L;

    }

    public interface Number {
        int THOUSAND_INT = 1000;
        int HUNDRED_INT = 100;
        int ONE_INT = 1;
        int TWO_INT = 2;
        int THREE_INT = 3;
        int FOUR_INT = 4;
        int FIVE_INT = 5;
        int SIX_INT = 6;
        int SEVEN_INT = 7;
        int EIGHT_INT = 8;
        int NINE_INT = 9;
        int TEN_INT = 10;
    }
    
}
