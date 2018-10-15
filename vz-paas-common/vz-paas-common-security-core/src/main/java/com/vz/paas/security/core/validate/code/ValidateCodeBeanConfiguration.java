package com.vz.paas.security.core.validate.code;

import com.google.code.kaptcha.Producer;
import com.vz.paas.security.core.properties.SecurityProperties;
import com.vz.paas.security.core.validate.code.image.ImageCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码相关的扩展点配置
 * 配置bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 17:13:50
 */
@Configuration
public class ValidateCodeBeanConfiguration {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private Producer kaptchaProducer;

    /**
     * 图片验证码生成器
     * @return 验证码
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageCodeGenerator generator = new ImageCodeGenerator();
        generator.setSecurityProperties(securityProperties);
        generator.setKaptchaProducer(kaptchaProducer);
        return generator;
    }

}
