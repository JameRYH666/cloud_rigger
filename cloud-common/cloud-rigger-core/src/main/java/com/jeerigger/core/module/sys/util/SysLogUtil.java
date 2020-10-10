/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeerigger.core.module.sys.util;

import com.jeerigger.core.common.user.BaseUser;
import com.jeerigger.core.module.sys.entity.SysLog;
import com.jeerigger.frame.annotation.Log;
import com.jeerigger.frame.enums.FlagEnum;
import com.jeerigger.frame.enums.LogTypeEnum;
import com.jeerigger.frame.support.util.UserAgentUtil;
import com.jeerigger.frame.util.StringUtil;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 日志工具类
 */
public class SysLogUtil {

    /**
     * 保存日志
     */
    public static void saveLog(BaseUser userData, HttpServletRequest request, String params, Log log) {
        saveLog(userData, request, null, params, log, 0);
    }

    /**
     * 保存日志
     *
     * @param executeTime
     */

    public static void saveLog(BaseUser userData, HttpServletRequest request, Throwable throwable, String params,
                               Log log, long executeTime) {
        saveLog(userData, request, throwable, params, log.logType(), log.logTitle(), executeTime);
    }

    /**
     * 保存日志
     *
     * @param userData
     * @param request
     * @param logType
     * @param logTitle
     */
    public static void saveLog(BaseUser userData, HttpServletRequest request, LogTypeEnum logType, String logTitle) {
        saveLog(userData, request, null, null, logType, logTitle, 0);
    }

    /**
     * 保存日志
     *
     * @param userData
     * @param request
     * @param throwable
     * @param params
     * @param logType
     * @param logTitle
     * @param executeTime
     */
    public static void saveLog(BaseUser userData, HttpServletRequest request, Throwable throwable, String params,
                               LogTypeEnum logType, String logTitle, long executeTime) {
        if (userData == null || Objects.isNull(userData.getUserId()) || request == null) {
            return;
        }
        SysLog sysLog = new SysLog();
        sysLog.setLogType(logType.getLogType());
        sysLog.setLogTitle(logTitle);
        sysLog.setRemoteAddr(UserAgentUtil.getUserIp(request));
        sysLog.setRequestUri(request.getRequestURL().toString());
        sysLog.setRequestMethod(request.getMethod());

        if (StringUtil.isNotEmpty(params)) {
            sysLog.setRequestParams(params);
        }
        UserAgent userAgent = UserAgentUtil.getUserAgent(request);
        sysLog.setExceptionFlag(FlagEnum.NO.getCode());
        sysLog.setDeviceName(userAgent.getOperatingSystem().getName());
        sysLog.setBrowserName(userAgent.getBrowser().getName());
        sysLog.setExceptionFlag(FlagEnum.NO.getCode());
        sysLog.setUserId(userData.getUserId());
        sysLog.setCreateUser(userData.getUserId());
        sysLog.setUserName(userData.getUserName());
        sysLog.setExecuteTime(new BigDecimal(executeTime));

        sysLog.setExceptionFlag(throwable != null ? FlagEnum.YES.getCode() : FlagEnum.NO.getCode());
        String exceStr = StringUtil.getExceptionAsString(throwable);
        if (StringUtil.isNotEmpty(exceStr)) {
            sysLog.setExceptionInfo(exceStr);
        }
        // 如果无地址并无异常日志，则不保存信息
        if (StringUtil.isEmpty(sysLog.getRequestUri()) && StringUtil.isEmpty(sysLog.getExceptionInfo())) {
            return;
        }

        SaveLogThread saveLogThread = SaveLogThread.getInstance();

        saveLogThread.queue.offer(sysLog);

        boolean alive = saveLogThread.isAlive();
        if (!alive) {
            saveLogThread.run();
        }
    }
}
