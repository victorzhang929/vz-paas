package com.vz.paas.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 验证工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 10:02:03
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateUtil {

    private static final String REG_MOBILE = "^((\\+86)|(86))?(13|15|17|18)\\d{9}$";
    private static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 验证手机号码是否合法
     * @param mobile 手机号码
     * @return 是否合法
     */
    public static boolean isMobileNumer(final String mobile) {
        return PublicUtil.isNotEmpty(mobile) && mobile.matches(REG_MOBILE);
    }

    /**
     * 验证邮箱地址是否合法
     * @param email 邮箱地址
     * @return 是否合法
     */
    public static boolean isEmail(final String email) {
        return PublicUtil.isNotEmpty(email) && email.matches(REG_EMAIL);
    }

}
