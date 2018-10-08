package com.vz.paas.config.properties;

import lombok.Data;

/**
 * 七牛配置信息
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-08 16:24:55
 */
@Data
public class QiniuProperties {

    private QiniuKeyProperties key = new QiniuKeyProperties();
    private QiniuOssProperties oss = new QiniuOssProperties();

    @Data
    private class QiniuKeyProperties {
        private String accessKey;
        private String secretKey;
    }

    @Data
    private class QiniuOssProperties {
        private String privateHost;
        private String publicHost;
        private Long fileMaxSize;
    }
}
