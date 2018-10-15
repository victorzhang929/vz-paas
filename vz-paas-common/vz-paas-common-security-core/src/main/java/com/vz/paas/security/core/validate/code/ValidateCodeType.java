package com.vz.paas.security.core.validate.code;

import com.vz.paas.security.core.properties.SecurityConstant;

/**
 * 验证码类型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-12 14:56:51
 */
public enum ValidateCodeType {

    /**
     * 短信验证码
     */
    SMS{
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstant.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstant.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    },
    /**
     * 邮箱验证码
     */
    EMAIL {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstant.DEFAULT_PARAMETER_NAME_CODE_EMAIL;
        }
    };

    /**
     * 获取参数名称
     * @return 参数名称
     */
    public abstract String getParamNameOnValidate();

}
