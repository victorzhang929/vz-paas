package com.vz.paas.security.core.validate.code.sms;

import com.vz.paas.security.core.properties.SecurityProperties;
import com.vz.paas.security.core.validate.code.ValidateCode;
import com.vz.paas.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码生成器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 10:41:14
 */
@Component("smsCodeValidateCodeGenerator")
public class SmsCodeValidateGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties properties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(properties.getCode().getSms().getLength());
        return new ValidateCode(code, properties.getCode().getSms().getExpireIn());
    }
}
