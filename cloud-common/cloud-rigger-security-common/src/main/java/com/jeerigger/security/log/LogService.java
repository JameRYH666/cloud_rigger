package com.jeerigger.security.log;

import com.jeerigger.frame.annotation.Log;
import com.jeerigger.security.user.JeeUser;

import java.util.List;
import java.util.Map;

public interface LogService {

    /**
     * 获取日志列表
     */
    List selectSysLogList();

    /**
     * 保存日志
     * */
    void saveLog(JeeUser userData, Map<String,String> request, Throwable throwable, String params, Log log, long executeTime);

    /**
     * 保存日志
     * */
    void saveLog(String userId,String userName, Map<String,String> request, Throwable throwable, String params, Log log, long executeTime);
}
