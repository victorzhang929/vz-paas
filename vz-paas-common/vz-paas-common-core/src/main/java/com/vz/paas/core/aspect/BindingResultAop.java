package com.vz.paas.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.vz.paas.core.annotation.ValidateAnnoation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * 绑定结果切面
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 17:03:49
 */
@Slf4j
@Aspect
@Component
public class BindingResultAop {

    @Pointcut("@annotation(com.vz.paas.core.annotation.ValidateAnnoation)")
    public void validateAnnotation(){}

    @Before("validateAnnotation()")
    public void before() {}

    @AfterReturning(pointcut = "validateAnnotation()")
    public void afterReturning(final JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();

        // 获取拦截的方法
        Method method = getMethodByClassAndName(target.getClass(), methodName);
        Object[] objs = joinPoint.getArgs();

        //方法参数
        assert method != null;
        ValidateAnnoation validate = (ValidateAnnoation) getAnnotationByMethod(method, ValidateAnnoation.class);
        if (validate != null) {
            BindingResult bindingResult = null;

            for (Object obj : objs) {
                if (obj instanceof BindingResult) {
                    bindingResult = (BindingResult) obj;
                }
            }
            if (bindingResult != null && bindingResult.hasErrors()) {
                String error = bindingResult.getFieldError().getDefaultMessage();
                throw new IllegalArgumentException(error);
            }
        }

    }

    /**
     * 根据方法获取注解
     * @param method 方法
     * @param clazz 类
     * @return 注解类，否则为null
     */
    private Annotation getAnnotationByMethod(Method method, Class clazz) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == clazz) {
                return annotation;
            }
        }

        return null;
    }

    /**
     * 根据类和方法名称得到方法
     * @param clazz 类
     * @param methodName 方法名
     * @return 方法类，否则为null
     */
    private Method getMethodByClassAndName(Class clazz, String methodName) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}
