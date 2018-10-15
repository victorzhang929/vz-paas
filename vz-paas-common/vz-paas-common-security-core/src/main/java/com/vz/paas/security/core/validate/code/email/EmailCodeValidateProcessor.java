package com.vz.paas.security.core.validate.code.email;

import java.util.Map;

import javax.annotation.Resource;

import com.vz.paas.security.core.properties.SecurityConstant;
import com.vz.paas.security.core.validate.code.ValidateCode;
import com.vz.paas.security.core.validate.code.ValidateCodeGenerator;
import com.vz.paas.security.core.validate.code.ValidateCodeRepository;
import com.vz.paas.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 邮件验证码处理器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 11:17:10
 */
@Component("emailValidateCodeProcessor")
public class EmailCodeValidateProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Resource
    private EmailCodeSender emailCodeSender;

    public EmailCodeValidateProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, ValidateCodeRepository validateCodeRepository) {
        super(validateCodeGenerators, validateCodeRepository);
    }

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstant.DEFAULT_PARAMETER_NAME_EMAIL;
        String email = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        emailCodeSender.send(email, validateCode.getCode());
    }
}
