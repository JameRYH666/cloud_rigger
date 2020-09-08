package com.jeerigger.security.handler;

import com.jeerigger.frame.base.controller.FastJSON;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.json.JSONUtil;
import com.jeerigger.security.StringUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JeeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        AuthenticationException e) throws IOException {
        FastJSON fastJSON = new FastJSON();
        if (e instanceof BadCredentialsException ||
                e instanceof UsernameNotFoundException) {
            fastJSON.setCode(ResultCodeEnum.ERROR_USER_PWD.getCode()).setMessage(ResultCodeEnum.ERROR_USER_PWD.getMessage());

        } else if (e instanceof LockedException) {
            fastJSON.setCode(ResultCodeEnum.ERROR_USER_DISABLE.getCode()).setMessage(ResultCodeEnum.ERROR_USER_DISABLE.getMessage());


        } else if (e instanceof CredentialsExpiredException) {
            fastJSON.setCode(ResultCodeEnum.ERROR_USER_PWD.getCode()).setMessage(ResultCodeEnum.ERROR_USER_PWD.getMessage());

            //   respBean = RespBean.error("密码过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            fastJSON.setCode(ResultCodeEnum.ERROR_USER_PWD.getCode()).setMessage(ResultCodeEnum.ERROR_USER_PWD.getMessage());

            //   respBean = RespBean.error("账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            fastJSON.setCode(ResultCodeEnum.ERROR_USER_FREEZE.getCode()).setMessage(ResultCodeEnum.ERROR_USER_FREEZE.getMessage());


        }else if (e instanceof InternalAuthenticationServiceException) {
            if (StringUtil.isNotEmpty(e.getMessage()) && e.getMessage().equals(ResultCodeEnum.ERROR_NO_PERMISSION.getCode())){
                fastJSON.setCode(ResultCodeEnum.ERROR_LOGIN_WRONG.getCode()).setMessage("没有配置菜单权限，请联系管理员！");
            }else {
                fastJSON.setCode(ResultCodeEnum.ERROR_LOGIN_WRONG.getCode()).setMessage(ResultCodeEnum.ERROR_LOGIN_WRONG.getMessage());
            }
        } else {
            fastJSON.setCode(ResultCodeEnum.ERROR_LOGIN_WRONG.getCode()).setMessage(ResultCodeEnum.ERROR_LOGIN_WRONG.getMessage());


        }
        JSONUtil.writeJson(resp, fastJSON);
    }
}
