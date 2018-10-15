package com.vz.paas.security.core.validate.code.sms;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vz.paas.core.util.RequestUtil;
import com.vz.paas.security.core.SecurityResult;
import com.vz.paas.security.core.properties.SecurityConstant;
import com.vz.paas.security.core.properties.SecurityProperties;
import com.vz.paas.security.core.properties.SmsCodeProperties;
import com.vz.paas.security.core.validate.code.ValidateCode;
import com.vz.paas.security.core.validate.code.ValidateCodeException;
import com.vz.paas.security.core.validate.code.ValidateCodeGenerator;
import com.vz.paas.security.core.validate.code.ValidateCodeRepository;
import com.vz.paas.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import com.vz.paas.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-15 10:44:20
 */
@Slf4j
@Component("smsValidateCodeProcessor")
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Resource
    private SmsCodeSender smsCodeSender;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SecurityProperties securityProperties;

    @Resource
    private ObjectMapper objectMapper;

    public SmsValidateCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, ValidateCodeRepository validateCodeRepository) {
        super(validateCodeGenerators, validateCodeRepository);
    }

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstant.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        String ipAddr = RequestUtil.getRemoteAddr(request.getRequest());
        SecurityResult result = new SecurityResult(SecurityResult.SUCCESS_CODE, "校验成功", true);
        // 统一处理短信流量
        try {
            this.checkSendSmsCount(mobile, ipAddr);
            smsCodeSender.send(mobile, validateCode.getCode(), ipAddr);
        } catch (ValidateCodeException e) {
            log.error("校验短信数量，e={}", e.getMessage(), e);
            result = SecurityResult.error(e.getMessage(), false);
        } catch (Exception e) {
            log.error("校验短信数量，e={}", e.getMessage(), e);
            result = SecurityResult.error("内部异常", false);
        }

        String json = objectMapper.writeValueAsString(result);
        HttpServletResponse response = request.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private void checkSendSmsCount(String mobile, String ipAddr) {
        String mobileSmsCountKey = RedisKeyUtil.getSendSmsCount(mobile, "mobile");
        String ipSmsCountKey = RedisKeyUtil.getSendSmsCount(ipAddr, "ip");
        String totalSmsCountKey = RedisKeyUtil.getSendSmsCount("total", "total");
        String sendSmsRateKey = RedisKeyUtil.getSendSmsRate(ipAddr);

        SmsCodeProperties properties = securityProperties.getCode().getSms();
        Integer sendSmsRateCount = (Integer) redisTemplate.opsForValue().get(sendSmsRateKey);
        if (sendSmsRateCount != null) {
            log.error("操作频率过快，ipAddr={}, mobile={}", ipAddr, mobile);
            throw new ValidateCodeException("操作频率过快");
        } else {
            redisTemplate.opsForValue().set(sendSmsRateKey, 1, 1, TimeUnit.MINUTES);
        }

        Integer mobileSmsCount = (Integer) redisTemplate.opsForValue().get(mobileSmsCountKey);
        if (mobileSmsCount != null && mobileSmsCount > properties.getMobileMaxSendCount()) {
            log.error("Mobile当天短信发送数已达上限，ipAddr={}, mobile={}", ipAddr, mobile);
            throw new ValidateCodeException("Mobile当天短信发送数已达上限");
        } else {
            redisTemplate.opsForValue().set(mobileSmsCountKey, mobileSmsCount == null ? 1 : mobileSmsCount + 1, 1, TimeUnit.DAYS);
        }

        Integer ipSmsCount = (Integer) redisTemplate.opsForValue().get(ipSmsCountKey);
        if (ipSmsCount != null && ipSmsCount > properties.getIpMaxSendCount()) {
            log.error("IP当天短信发送数量已达上限，ipAddr={}, mobile={}", ipAddr, mobile);
            throw new ValidateCodeException("IP当天短信发送数量已达上限");
        } else {
            redisTemplate.opsForValue().set(ipSmsCountKey, ipSmsCount == null ? 1 : ipSmsCount + 1, 1, TimeUnit.DAYS);
        }

        Integer totalSmsCount = (Integer) redisTemplate.opsForValue().get(totalSmsCountKey);
        if (totalSmsCount != null && totalSmsCount > properties.getTotalMaxSendCount()) {
            log.error("当天短信发送数量已达上限，ipAddr={}, mobile={}", ipAddr, mobile);
            throw new ValidateCodeException("当天短信发送数量已达上限");
        } else {
            redisTemplate.opsForValue().set(totalSmsCountKey, totalSmsCount == null ? 1 : totalSmsCount + 1, 1, TimeUnit.DAYS);
        }
    }

}
