package com.vz.paas.security.core.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.vz.paas.security.core.validate.code.ValidateCode;
import lombok.Data;

/**
 * 图片验证码
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 17:26:23
 */
@Data
public class ImageCode extends ValidateCode {

    private static final long serialVersionUID = -37545979963629874L;

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }
}
