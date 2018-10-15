package com.vz.paas.security.core.validate.code.impl;

import java.util.Map;

import com.vz.paas.security.core.validate.code.ValidateCode;
import com.vz.paas.security.core.validate.code.ValidateCodeException;
import com.vz.paas.security.core.validate.code.ValidateCodeGenerator;
import com.vz.paas.security.core.validate.code.ValidateCodeProcessor;
import com.vz.paas.security.core.validate.code.ValidateCodeRepository;
import com.vz.paas.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 抽象图片验证码处理器
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 17:42:24
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 收集系统搜索的接口实现
     */
    private final Map<String, ValidateCodeGenerator> validateCodeGenerators;

    private final ValidateCodeRepository validateCodeRepository;

    @Autowired
    public AbstractValidateCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, ValidateCodeRepository validateCodeRepository) {
        this.validateCodeGenerators = validateCodeGenerators;
        this.validateCodeRepository = validateCodeRepository;
    }


    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType type = getValidateCodeType();
        this.check(request);
        validateCodeRepository.remove(request, type);
    }

    /**
     * 发送验证码，由子类实现
     *
     * @param request      请求
     * @param validateCode 验证码
     * @throws Exception 异常
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 报错验证码
     *
     * @param request      请求
     * @param validateCode 验证码
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType());
    }

    /**
     * 生成验证码
     *
     * @param request 请求
     * @return 验证码
     */
    private C generate(ServletWebRequest request) {
        String type = String.valueOf(getValidateCodeType()).toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);

        if (null == validateCodeGenerator) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }

        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 根据请求的url获取验证码的类型
     *
     * @return 验证码类型
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    @Override
    public void check(ServletWebRequest request) {
        ValidateCodeType type = getValidateCodeType();
        C codeInSession = (C) validateCodeRepository.get(request, type);
        String codeInRequest;

        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), type.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(type + "验证码的值不能为空");
        }

        if (codeInSession == null || codeInSession.isExpired()) {
            validateCodeRepository.remove(request, type);
            throw new ValidateCodeException(type + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(type + "验证码不匹配");
        }
    }
}
