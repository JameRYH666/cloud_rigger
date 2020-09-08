package com.jeerigger.security.handler;

import com.jeerigger.frame.base.controller.FastJSON;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.json.JSONUtil;
import com.jeerigger.security.SecurityUtil;
import com.jeerigger.security.user.JeeUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JeeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        Authentication auth) {
        FastJSON<JeeUser> fastJson = new FastJSON().
                setCode(ResultCodeEnum.SUCCESS.getCode()).
                setMessage(ResultCodeEnum.SUCCESS.getMessage());
        JeeUser jeeUser = SecurityUtil.getUser();
        jeeUser.setSessionId(SecurityUtil.getSessionId());
        jeeUser.setToken(SecurityUtil.getSessionId());
        fastJson.ex(JeeUser::getAuthorities, JeeUser::isAccountNonExpired
                , JeeUser::isAccountNonLocked, JeeUser::isCredentialsNonExpired, JeeUser::isEnabled
                , JeeUser::getUserType, JeeUser::getUsername).data(jeeUser);
        JSONUtil.writeJson(resp, fastJson);

    }

}
