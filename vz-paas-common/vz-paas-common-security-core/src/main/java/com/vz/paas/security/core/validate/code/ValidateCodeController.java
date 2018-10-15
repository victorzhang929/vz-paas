package com.vz.paas.security.core.validate.code;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vz.paas.security.core.SecurityResult;
import com.vz.paas.security.core.properties.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 生成验证码的请求处理器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 16:59:32
 */
@Slf4j
@RestController
public class ValidateCodeController {

    @Resource
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的接口实现
     * @param request 请求
     * @param response 响应
     * @param type 类型
     * @throws Exception 异常
     */
    @PostMapping(SecurityConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }

    /**
     * 验证验证码
     * @param request 请求
     * @param response 响应
     * @param type 类型
     * @return 验证结果
     */
    @GetMapping(SecurityConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public Object checkCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) {
        SecurityResult result = new SecurityResult(SecurityResult.SUCCESS_CODE, "校验成功", true);
        try {
            validateCodeProcessorHolder.findValidateCodeProcessor(type).check(new ServletWebRequest(request, response));
        } catch (ValidateCodeException e) {
            result = SecurityResult.error(e.getMessage(), false);
        } catch (Exception e) {
            log.error("getAccessToken={}", e.getMessage(), e);
            result = SecurityResult.error("验证码错误", false);
        }

        return result;
    }

}
