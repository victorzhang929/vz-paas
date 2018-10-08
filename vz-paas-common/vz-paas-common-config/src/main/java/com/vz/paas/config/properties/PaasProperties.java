package com.vz.paas.config.properties;

import com.vz.paas.base.constant.GlobalConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Paas配置信息
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-08 16:52:59
 */
@Data
@ConfigurationProperties(prefix = GlobalConstant.ROOT_PREFIX)
public class PaasProperties {

    private ReliableMessageProperties message = new ReliableMessageProperties();

    private AliyunProperties aliyun = new AliyunProperties();

    private AsyncTaskProperties task = new AsyncTaskProperties();

    private SwaggerProperties swagger = new SwaggerProperties();

    private QiniuProperties qiniu = new QiniuProperties();

    private AmapProperties amap = new AmapProperties();

    private JobProperties job = new JobProperties();

    private ZookeeperPropertiest zk = new ZookeeperPropertiest();
}
