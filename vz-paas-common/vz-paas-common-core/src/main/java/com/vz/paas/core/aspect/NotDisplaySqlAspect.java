package com.vz.paas.core.aspect;

import com.vz.paas.util.ThreadLocalMapUtil;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 不用展示sql切面
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 16:59:57
 */
@Aspect
@Component
public class NotDisplaySqlAspect {

    public static final String DISPLAY_SQL = "DISPLAY_SQL";

    @Pointcut("@annotation(com.vz.paas.core.annotation.NotDisplaySqlAnnotation)")
    private void pointCut(){}

    @Before("pointCut()")
    public void before() {
        ThreadLocalMapUtil.put(DISPLAY_SQL, Boolean.FALSE);
    }

    @After("pointCut()")
    public void after() {
        ThreadLocalMapUtil.remove(DISPLAY_SQL);
    }
}
