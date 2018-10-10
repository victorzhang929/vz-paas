package com.vz.paas.core.interceptor;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

import com.google.common.collect.Lists;
import com.vz.paas.base.constant.GlobalConstant;
import com.vz.paas.core.aspect.NotDisplaySqlAspect;
import com.vz.paas.util.ThreadLocalMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

/**
 * 数据库日志拦截器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 17:16:15
 */
@Slf4j
@Order(1)
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class SqlLogInterceptor implements Interceptor {

    @Value("${paas.enable-sql-log-interceptor}")
    private boolean enableSqlLogInterceptor;

    /**
     * 关注时间，单位秒，默认值5
     * 如果执行sql超过时间，就会打印error日志
     */
    private Double noticeTime = 5.0;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();

        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        BoundSql boundSql = statement.getBoundSql(parameter);
        Configuration configuration = statement.getConfiguration();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<String> parameters = listParameter(configuration, boundSql);
        Object proceed = invocation.proceed();
        int result = 0;

        if (proceed instanceof ArrayList) {
            ArrayList resultList = (ArrayList) proceed;
            result = resultList.size();
        }
        if (proceed instanceof Integer) {
            result = (Integer) proceed;
        }
        if (enableSqlLogInterceptor) {
            long end = System.currentTimeMillis();
            long time = end - start;
            Boolean flag = (Boolean) ThreadLocalMapUtil.get(NotDisplaySqlAspect.DISPLAY_SQL);
            if (time >= noticeTime * GlobalConstant.Number.THOUSAND_INT) {
                log.error("执行超过{}秒，sql={}", noticeTime, sql);
                log.error("result={}, time={}ms, parameters={}", result, time, parameters);
                return proceed;
            }
            if (flag == null || Objects.equals(flag, true)) {
                log.info("sql={}", sql);
                log.error("result={}, time={}ms, parameters={}", result, time, parameters);
            }

            return proceed;
        }
        return null;
    }

    /**
     * 获取sql参数集合
     * @param configuration 配置类
     * @param boundSql sql
     * @return sql参数集合
     */
    private List<String> listParameter(Configuration configuration, BoundSql boundSql) {
        Object obj = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<String> parameters = Lists.newArrayList();

        if (parameterMappings.size() > 0 && obj != null) {
            TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
            if (registry.hasTypeHandler(obj.getClass())) {
                parameters.add(getParameterValue(obj));
            } else {
                MetaObject meta = configuration.newMetaObject(obj);
                for (ParameterMapping mapping : parameterMappings) {
                    String propertyName = mapping.getProperty();
                    if (meta.hasGetter(propertyName)) {
                        Object object = meta.getValue(propertyName);
                        parameters.add(getParameterValue(object));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object object = boundSql.getAdditionalParameter(propertyName);
                        parameters.add(getParameterValue(object));
                    }
                }
            }
        }
        return parameters;
    }

    /**
     * 获取参数值
     * @param obj 对象
     * @return 参数值
     */
    private String getParameterValue(Object obj) {
        String value;

        if (obj instanceof String) {
            value = "'" + String.valueOf(obj) + "'";
        } else if (obj instanceof Date) {
            DateFormat format = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + format.format(obj) + "'";
        } else {
            if (obj != null) {
                value = String.valueOf(obj);
            } else {
                value = "";
            }
        }

        return value;
    }

    public SqlLogInterceptor() {
        log.info("[打印sql拦截器创建]noticeTime={}秒", noticeTime);
    }

    public SqlLogInterceptor(Double noticeTime) {
        this.noticeTime = noticeTime;
        log.info("[打印sql拦截器创建]noticeTime={}秒", noticeTime);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
