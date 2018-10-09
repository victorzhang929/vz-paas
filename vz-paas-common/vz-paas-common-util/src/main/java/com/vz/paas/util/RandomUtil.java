package com.vz.paas.util;

import java.util.Random;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 随机数工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 09:42:50
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtil {

    private static final int MAX_LENTH = 50;

    /**
     * 生成复杂随机验证码，大小写字母+数字
     * @param length 验证码长度
     * @return 随机验证码
     */
    public static String createComplexCode(int length) {
        if (length > MAX_LENTH) {
            length = MAX_LENTH;
        }

        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int tmp = random.nextInt(127);
            if (tmp < 33 || tmp == 92 || tmp == 47 || tmp == 34) {
                continue;
            }
            char c = (char) tmp;
            if (String.valueOf(code).indexOf(c) > 0) {
                continue;
            }
            code.append(c);
        }
        return String.valueOf(code);
    }

    /**
     * 生成数字随机验证码
     * @param length 验证码长度
     * @return 随机验证码
     */
    public static String createNumberCode(int length) {
        return randomString("0123456789", length);
    }

    private static String randomString(String base, int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        if (length < 1) {
            length = 1;
        }
        int baseLength = base.length();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(baseLength);
            builder.append(base.charAt(number));
        }

        return String.valueOf(builder);
    }
}
