package com.jeerigger.security.aspect;

import com.alibaba.fastjson.JSONObject;
import com.jeerigger.frame.annotation.Log;
import com.jeerigger.frame.support.util.UserAgentUtil;
import com.jeerigger.security.SecurityUtil;
import com.jeerigger.security.log.LogService;
import com.jeerigger.security.user.JeeUser;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Ian
 */
@Aspect
public class SysLogAspect {

    public SysLogAspect(LogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    private LogService sysLogService;

    @Around("@annotation(log)")
    public Object around(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        //执行开始时间
        long beginTime = System.currentTimeMillis();
        Throwable throwable = null;
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            throwable = ex;
            throw throwable;
        } finally {
            //执行结束时间
            long endTime = System.currentTimeMillis();
            saveLog(joinPoint, throwable, endTime - beginTime);
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint, Throwable throwable, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        if (log != null) {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            JeeUser userData = SecurityUtil.getUserData();
            if (userData != null && Objects.nonNull(userData.getUserId()) && request != null) {
                List<Object> paramsObj = new ArrayList<>();
                String params = "";
                try {
                    for (Object obj : joinPoint.getArgs()) {
                        if (obj instanceof HttpServletRequest || obj instanceof HttpServletResponse || obj instanceof MultipartFile) {
                            continue;
                        }
                        paramsObj.add(obj);
                    }
                    params = JSONObject.toJSONString(paramsObj.toArray(new Object[paramsObj.size()]));
                } catch (Exception ex) {
                }
                Map<String, String> requestMap = new HashMap<>(5);
                requestMap.put(UserAgentUtil.REQUEST_URI, request.getRequestURL().toString());
                requestMap.put(UserAgentUtil.REQUEST_METHOD, request.getMethod());
                UserAgent userAgent = UserAgentUtil.getUserAgent(request);
                requestMap.put(UserAgentUtil.DEVICE_NAME, userAgent.getOperatingSystem().getName());
                requestMap.put(UserAgentUtil.BROWSER_NAME, userAgent.getBrowser().getName());

                sysLogService.saveLog(userData, requestMap, throwable, params, log, time);
            }
        }
    }
}
