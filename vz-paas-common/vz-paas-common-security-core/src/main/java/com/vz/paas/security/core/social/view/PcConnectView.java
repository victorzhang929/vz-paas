package com.vz.paas.security.core.social.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * 绑定结果视图
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:04:06
 */
public class PcConnectView extends AbstractView {

    private static final String CONNECTIONS = "connections";

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/html;charset=UTF-8");
        if (map.get(CONNECTIONS) == null) {
            response.getWriter().write("<h3>解绑成功</h3>");
        } else {
            response.getWriter().write("<h3>绑定成功</h3>");
        }

    }
}
