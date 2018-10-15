package com.vz.paas.security.core.validate.code.image;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vz.paas.security.core.SecurityResult;
import com.vz.paas.security.core.validate.code.ValidateCodeGenerator;
import com.vz.paas.security.core.validate.code.ValidateCodeRepository;
import com.vz.paas.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 图片验证码处理器
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 17:33:49
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Resource
    private ObjectMapper mapper;

    public ImageCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, ValidateCodeRepository validateCodeRepository) {
        super(validateCodeGenerators, validateCodeRepository);
    }

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(imageCode.getImage(), "JPEG", bos);

        SecurityResult result = SecurityResult.ok(bos.toByteArray());

        String json = mapper.writeValueAsString(result);
        HttpServletResponse response = request.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
