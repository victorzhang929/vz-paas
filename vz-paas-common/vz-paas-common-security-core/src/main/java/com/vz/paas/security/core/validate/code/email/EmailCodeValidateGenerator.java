package com.vz.paas.security.core.validate.code.email;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vz.paas.security.core.properties.SecurityProperties;
import com.vz.paas.security.core.validate.code.ValidateCode;
import com.vz.paas.security.core.validate.code.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 邮件验证码生成器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 11:13:13
 */
@Slf4j
@Component("emailCodeValidateGenerator")
public class EmailCodeValidateGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties properties;


    @Override
    public ValidateCode generate(ServletWebRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Map<String, String[]> parameters = httpServletRequest.getParameterMap();
        String[] emails = parameters.get("email");

        String code = Arrays.toString(emails);
        log.info(code);
        return new ValidateCode(code, properties.getCode().getEmail().getExpireIn());
    }
}
