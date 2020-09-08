package com.jeerigger.core.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.jeerigger.core.common.annotation.Log;
import com.jeerigger.core.common.user.BaseUser;
import com.jeerigger.core.module.sys.util.SysLogUtil;
import com.jeerigger.frame.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class SysLogAspect {
    /**
     * 定义切点(切BaseController以及所有继承了BaseController的子类)
     */
    @Pointcut("execution(public * com.jeerigger.frame.base.controller.BaseController+.*(..)))")
    public void sysLog() {

    }

    /**
     * 环绕基类为BaseController进行业务处理
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("sysLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();//执行开始时间
        Throwable throwable = null;
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            throwable = ex;
            throw throwable;
        } finally {
            long endTime = System.currentTimeMillis();//执行结束时间
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
            BaseUser userData = null; // ShiroUtil.getUserData();
            if (userData != null && StringUtil.isNotEmpty(userData.getUserUuid()) && request != null) {
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
                SysLogUtil.saveLog(userData, request, throwable, params, log, time);
            }
        }
    }
}
