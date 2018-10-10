package com.vz.paas.core.aspect;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.vz.paas.base.constant.GlobalConstant;
import com.vz.paas.base.dto.LoginAuthDto;
import com.vz.paas.core.annotation.LogAnnotation;
import com.vz.paas.core.dto.OperationLogDto;
import com.vz.paas.core.util.RequestUtil;
import com.vz.paas.util.JacksonUtil;
import com.vz.paas.util.wrapper.Wrapper;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 日志切面
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 16:03:46
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private TaskExecutor taskExecutor;

    private static final int MAX_SIZE = 2000;

    @Pointcut("@annotation(com.vz.paas.core.annotation.LogAnnotation)")
    public void logAnnotation() {
    }

    @Before("logAnnotation()")
    public void before() {
        this.threadLocal.set(new Date(System.currentTimeMillis()));
    }

    @AfterReturning(pointcut = "logAnnotation()", returning = "value")
    public void afterReturning(final JoinPoint joinPoint, final Object value) {
        if (value instanceof Wrapper) {
            Wrapper result = (Wrapper) value;

            if (result.getCode() == Wrapper.SUCCESS_CODE) {
                this.handleLog(joinPoint, result);
            }
        }
    }

    private void handleLog(final JoinPoint joinPoint, final Object result) {
        final Date startTime = this.threadLocal.get();
        final Date endTime = new Date(System.currentTimeMillis());

        HttpServletRequest request = RequestUtil.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(GlobalConstant.USER_AGENT));
        String reuqestURI = request.getRequestURI();

        try {
            LogAnnotation log = giveController(joinPoint);
            LoginAuthDto loginUser = RequestUtil.getLoginUser();
            if (log == null) {
                return;
            }

            final String os = userAgent.getOperatingSystem().getName();
            final String browser = userAgent.getBrowser().getName();
            final String ip = RequestUtil.getRemoteAddr(request);

            OperationLogDto logDto = new OperationLogDto();

            logDto.setClassName(joinPoint.getTarget().getClass().getName());
            logDto.setMethodName(joinPoint.getSignature().getName());
            logDto.setExecuteTime(endTime.getTime() - startTime.getTime());
            logDto.setStartTime(startTime);
            logDto.setEndTime(endTime);
            logDto.setIp(ip);
            logDto.setOs(os);
            logDto.setBrowser(browser);
            logDto.setRequestUrl(reuqestURI);

            logDto.setGroupId(loginUser.getGroupId());
            logDto.setGroupName(loginUser.getGroupName());
            logDto.setCreatedTime(new Date());
            logDto.setCreator(loginUser.getUserName());
            logDto.setCreatorId(loginUser.getUserId());
            logDto.setLastOperator(loginUser.getUserName());
            logDto.setLastOperatorId(loginUser.getUserId());

            logDto.setLogType(log.logType().getType());
            logDto.setLogName(log.logType().getName());

            getControllerMethodDescription(log, logDto, result, joinPoint);
            threadLocal.remove();
            taskExecutor.execute(() -> this.restTemplate.postForObject("http://paas-provider-uac/auth/saveLog", logDto, Integer.class));
        } catch (Exception ex) {
            log.error("获取注解类出现异常={}", ex.getMessage(), ex);
        }
    }

    /**
     * 获取控制器方法描述
     * @param logAnnotation 日志注解
     * @param logDto 日志数据传输对象
     * @param result 结果
     * @param joinPoint 切点
     */
    private void getControllerMethodDescription(LogAnnotation logAnnotation, OperationLogDto logDto, Object result, JoinPoint joinPoint) {
        if (logAnnotation.isSaveRequest()) {
            setRequest(logDto, joinPoint);
        }

        if (logAnnotation.isSaveResponse()) {
            setResponse(logDto, result);
        }
    }

    /**
     * 设置响应数据
     * @param logDto 日志数据传输对象
     * @param result 结果
     */
    private void setResponse(OperationLogDto logDto, Object result) {
        try {
            logDto.setResponse(String.valueOf(result));
        } catch (Exception e) {
            log.error("获取响应数据出现错误={}", e.getMessage(), e);
        }
    }

    /**
     * 设置请求数据
     * @param logDto 日志数据传输对象
     * @param joinPoint 切点
     */
    private void setRequest(OperationLogDto logDto, JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            if (args.length == 0) {
                return;
            }
            Object[] parameters = new Object[args.length];
            int index = 0;
            for (Object obj : parameters) {
                if (obj instanceof HttpServletRequest) {
                    continue;
                }
                parameters[index] = obj;
                index++;
            }

            String request = JacksonUtil.toJsonWithFormat(parameters);
            if (request.length() > MAX_SIZE) {
                request = request.substring(MAX_SIZE);
            }

            logDto.setRequest(request);
        } catch (IOException e) {
            log.error("获取请求数据出现错误={}", e.getMessage(), e);
        }
    }

    /**
     * 是否存在注解，如果存在就记录日志
     * @param joinPoint 切点
     * @return 存在返回对象，否则返回null
     */
    private static LogAnnotation giveController(JoinPoint joinPoint) {
        Method[] methods = joinPoint.getTarget().getClass().getDeclaredMethods();
        String methodName = joinPoint.getSignature().getName();

        if (null != methods && 0 < methodName.length()) {
            for (Method method : methods) {
                LogAnnotation log = method.getAnnotation(LogAnnotation.class);
                if (null != log && methodName.equals(method.getName())) {
                    return log;
                }
            }
        }

        return null;
    }
}
