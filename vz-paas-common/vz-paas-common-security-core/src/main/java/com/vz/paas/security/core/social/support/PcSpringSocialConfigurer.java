package com.vz.paas.security.core.social.support;

import lombok.Data;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 继承默认的社交登录配置，加入自定义后处理逻辑
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 16:34:21
 */
@Data
public class PcSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    private SocialAuthenticationFilterPostProcessor processor;

    public PcSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        if (processor != null) {
            processor.process(filter);
        }

        return (T) filter;
    }

}
