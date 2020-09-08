package com.jeerigger.security;

import com.jeerigger.security.user.JeeUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * Spring Security工具类
 */
public class SecurityUtil {

    //验证码常量
    private static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";


    /**
     * 获取用户信息
     *
     * @return
     */
    public static JeeUser getUserData(Authentication subject) {
        if (subject != null) {
            return (JeeUser) subject.getPrincipal();
        } else {
            return null;
        }
    }

    public static JeeUser getUserData() {
        Authentication authentication = getAuthentication();
        return getUserData(authentication);
    }


    public static String getSessionId() {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }

    public static JeeUser getUser() {
        Authentication authentication = getAuthentication();
        return getUserData(authentication);
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        Authentication authentication = getAuthentication();
        JeeUser baseUser = getUserData(authentication);
        if (baseUser != null) {
            return getUserData(authentication).getUserId();
        } else {
            return null;
        }
    }
}
