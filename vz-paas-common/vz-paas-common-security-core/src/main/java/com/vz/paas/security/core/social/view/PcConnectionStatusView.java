package com.vz.paas.security.core.social.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 * 社交账号绑定状态视图
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 17:07:13
 */
@Component("connect/status")
public class PcConnectionStatusView extends AbstractView {

    @Autowired
    private ObjectMapper mapper;


    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) map.get("connectionMap");

        Map<String, Boolean> result = new HashMap<>(8);
        for (String key : connections.keySet()) {
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(mapper.writeValueAsString(result));
    }
}
