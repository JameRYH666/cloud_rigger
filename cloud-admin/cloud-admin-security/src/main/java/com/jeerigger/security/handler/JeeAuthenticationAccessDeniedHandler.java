package com.jeerigger.security.handler;


import com.jeerigger.frame.base.controller.FastJSON;
import com.jeerigger.frame.json.JSONUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sang on 2017/12/29.
 */
@Component
public class JeeAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp, AccessDeniedException e) {
        FastJSON fastJSON = new FastJSON();
        fastJSON.setMessage("权限不足!");
        JSONUtil.writeJson(resp, fastJSON, HttpServletResponse.SC_FORBIDDEN);

    }
}
