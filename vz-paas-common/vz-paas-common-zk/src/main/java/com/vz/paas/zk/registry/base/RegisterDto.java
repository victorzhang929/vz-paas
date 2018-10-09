package com.vz.paas.zk.registry.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 注册数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:35:44
 */
@Data
@AllArgsConstructor
public class RegisterDto {

    private String app;
    private String host;

    private CoordinatorRegistryCenter coordinatorRegistryCenter;
}
