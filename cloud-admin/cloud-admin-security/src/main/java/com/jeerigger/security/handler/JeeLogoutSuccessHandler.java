package com.jeerigger.security.handler;

import com.jeerigger.frame.base.controller.FastJSON;
import com.jeerigger.frame.json.JSONUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//退出操作提示
@Component
public class JeeLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        FastJSON fastJSON = new FastJSON();
        fastJSON.setCode("1100").setMessage("退出成功!");
        JSONUtil.writeJson(response, fastJSON);
    }
}
