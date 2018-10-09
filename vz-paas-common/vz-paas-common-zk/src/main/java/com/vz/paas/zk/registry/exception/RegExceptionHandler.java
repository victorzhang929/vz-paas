package com.vz.paas.zk.registry.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;

/**
 * 注册中心异常处理类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 16:07:18
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegExceptionHandler {

    /**
     * 处理异常
     * 处理掉中断异常和连接失效的异常并继续注册中心
     * @param cause 异常类
     */
    public static void handleException(final Exception cause) {
        if (null == cause) {
            return;
        }

        boolean flag = isIgnoredException(cause) || null != cause.getCause() && isIgnoredException(cause.getCause());
        if (flag) {
            log.debug("Elastic job: ignored exception for: {}", cause.getMessage());
        } else if (cause instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        } else {
            throw new RegException(cause);
        }

    }

    private static boolean isIgnoredException(final Throwable cause) {
        return cause instanceof KeeperException.NoNodeException || cause instanceof KeeperException.NodeExistsException;
    }
}
