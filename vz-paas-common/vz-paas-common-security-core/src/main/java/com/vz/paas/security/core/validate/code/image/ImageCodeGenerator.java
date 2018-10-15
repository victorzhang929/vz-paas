package com.vz.paas.security.core.validate.code.image;

import java.awt.image.BufferedImage;

import com.google.code.kaptcha.Producer;
import com.vz.paas.security.core.properties.SecurityProperties;
import com.vz.paas.security.core.validate.code.ValidateCode;
import com.vz.paas.security.core.validate.code.ValidateCodeGenerator;
import lombok.Setter;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 默认图片验证码生成器
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 17:30:15
 */
@Setter
public class ImageCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;
    private Producer kaptchaProducer;

    /**
     * 生成图片验证码
     * @param request 请求
     * @return 验证码
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String kaptchaCode = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(kaptchaCode);
        return new ImageCode(image, kaptchaCode, securityProperties.getCode().getImage().getExpireIn());
    }


}
