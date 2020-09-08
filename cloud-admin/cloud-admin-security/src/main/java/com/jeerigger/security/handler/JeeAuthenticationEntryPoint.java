package com.jeerigger.security.handler;

import com.jeerigger.frame.base.controller.FastJSON;
import com.jeerigger.frame.json.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jeerigger.frame.base.controller.ResultCodeEnum.ERROR_NO_LOGIN;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

//认真未登陆请求需要授权资源，返回401
@Component
public class JeeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        FastJSON fastJSON = new FastJSON().resultcode(ERROR_NO_LOGIN);
        JSONUtil.writeJson(response, fastJSON, SC_UNAUTHORIZED);

    }

}
