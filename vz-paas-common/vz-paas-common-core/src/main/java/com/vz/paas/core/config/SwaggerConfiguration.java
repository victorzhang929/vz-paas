package com.vz.paas.core.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import com.vz.paas.config.properties.PaasProperties;
import com.vz.paas.config.properties.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 19:28:07
 */
@EnableSwagger2
public class SwaggerConfiguration {

    @Resource
    private PaasProperties properties;

    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .enable(true);
    }

    private ApiInfo apiInfo() {
        SwaggerProperties swagger = properties.getSwagger();
        return new ApiInfoBuilder()
                .title(swagger.getTitle())
                .description(swagger.getDescription())
                .version(swagger.getVersion())
                .license(swagger.getLicense())
                .contact(new Contact(swagger.getContactName(), swagger.getContactUrl(), swagger.getContactEmail()))
                .build();
    }

    private List<ApiKey> securitySchemes() {
        return new ArrayList<>(Collections.singleton(new ApiKey("Authorization", "Authorization", "header")));
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList<>(Collections.singleton(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build()));
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = scope;
        return new ArrayList<>(Collections.singleton(new SecurityReference("Authorization", scopes)));
    }
}
