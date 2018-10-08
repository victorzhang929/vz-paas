package com.vz.paas.config.properties;

import lombok.Data;

/**
 * Swagger配置信息
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-08 16:21:08
 */
@Data
public class SwaggerProperties {

    private String title;

    private String description;

    private String version = "1.0-SNAPSHOT";

    private String license = "Apache License 2.0";

    private String contactName = "vz";

    private String contactUrl = "https://www.github.com/victorzhang929/";

    private String contactEmail = "victorzhang929@gmail.com";
}
