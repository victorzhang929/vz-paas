package com.vz.paas.security.core.validate.code;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证码处理器管理器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 16:22:53
 */
@Component
public class ValidateCodeProcessorHolder {

    private final Map<String, ValidateCodeProcessor> validateCodeProcessor;

    @Autowired
    public ValidateCodeProcessorHolder(Map<String, ValidateCodeProcessor> validateCodeProcessor) {
        this.validateCodeProcessor = validateCodeProcessor;
    }

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(String.valueOf(type).toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessor.get(name);

        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }

        return processor;
    }
}
