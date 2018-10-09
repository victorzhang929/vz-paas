package com.vz.paas.util;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 14:20:40
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BigDecimalUtil {

    /**
     * 加法
     * @param one 其一加数
     * @param another 其一加数
     * @return 和
     */
    public static BigDecimal add(double one, double another) {
        BigDecimal bdOne = new BigDecimal(Double.toString(one));
        BigDecimal bdAnother = new BigDecimal(Double.toString(another));

        return bdOne.add(bdAnother);
    }

    /**
     * 减法
     * @param one 减数
     * @param another 被减数
     * @return 差
     */
    public static BigDecimal substract(double one, double another) {
        BigDecimal bdOne = new BigDecimal(Double.toString(one));
        BigDecimal bdAnother = new BigDecimal(Double.toString(another));

        return bdOne.subtract(bdAnother);
    }

    /**
     * 乘法
     * @param one 乘数
     * @param another 被乘数
     * @return 积
     */
    public static BigDecimal multiply(double one, double another) {
        BigDecimal bdOne = new BigDecimal(Double.toString(one));
        BigDecimal bdAnother = new BigDecimal(Double.toString(another));

        return bdOne.multiply(bdAnother);
    }

    /**
     * 除法
     * @param one 除数
     * @param another 被除数
     * @return 商
     */
    public static BigDecimal divide(double one, double another) {
        BigDecimal bdOne = new BigDecimal(Double.toString(one));
        BigDecimal bdAnother = new BigDecimal(Double.toString(another));

        return bdOne.divide(bdAnother);
    }
}
