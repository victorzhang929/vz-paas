package com.vz.paas.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证注解
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 15:50:30
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateAnnoation {

    /**
     * 是否开启验证
     * @return 默认开启
     */
    boolean isValidate() default true;
}
