package com.vz.paas.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vz.paas.core.enums.LogTypeEnum;

/**
 * 操作日志注解
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 15:35:30
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    /**
     * 日志类型
     * @return 类型，默认为操作日志
     */
    LogTypeEnum logType() default LogTypeEnum.OPERATION_LOG;

    /**
     * 是否保存请求参数
     * @return 默认false
     */
    boolean isSaveRequest() default false;

    /**
     * 是否保存响应的结果
     * @return 默认false
     */
    boolean isSaveResponse() default false;
}
