package com.vz.paas.core.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring上下文辅助类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 15:27:41
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    /**
     * 应用上下文
     */
    private static ApplicationContext context;


    /**
     * 设置应用上下文
     * @param applicationContext 上下文
     * @throws BeansException 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.context = applicationContext;
    }

    /**
     * 获取应用上下文
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return context;
    }

    /**
     * 获取spring容器中bean
     * @param beanName bean名称
     * @param <T> 类型
     * @return bean实体
     */
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) context.getBean(beanName);
    }

    /**
     * 获取DefaultListableBeanFactory
     * @return DefaultListableBeanFactory
     */
    public static DefaultListableBeanFactory getDefaultListableBeanFactory() {
        assertApplicationContext();
        return (DefaultListableBeanFactory) ((ConfigurableApplicationContext) context).getBeanFactory();
    }

    /**
     * 验证Spring容器上下文是否注入成功，失败抛出异常
     */
    private static void assertApplicationContext() {
        if (SpringContextHolder.context == null) {
            throw new IllegalArgumentException("application context 属性为null，请检查是否注入了SpringContextHolder");
        }
    }
}
