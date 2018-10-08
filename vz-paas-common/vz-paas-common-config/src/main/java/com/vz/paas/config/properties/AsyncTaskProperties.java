package com.vz.paas.config.properties;

import lombok.Data;

/**
 * 异步任务配置信息
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-08 16:40:02
 */
@Data
public class AsyncTaskProperties {

    private int corePoolSize = 50;

    private int maxPoolSize = 100;

    private int queueCapacity = 10000;

    private int keepAliveSeconds = 3000;

    private String threadNamePrefix = "vz-paas-task-executor-";
}
