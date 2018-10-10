package com.vz.paas.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配合SqlLogInterceptor对指定方法，进制打印sql到控制台
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 15:48:55
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotDisplaySqlAnnotation {
}
