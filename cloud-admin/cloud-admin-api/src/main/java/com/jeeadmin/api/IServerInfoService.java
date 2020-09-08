package com.jeeadmin.api;

import java.util.List;
import java.util.Map;

public interface IServerInfoService {
    /**
     * 获取JVM信息
     *
     * @return
     */
    Map<String, String> getJvmInfo();

    /**
     * 获取系统信息
     *
     * @return
     */
    Map<String, String> getSystemInfo();

    /**
     * 获取内存信息
     *
     * @return
     */
    Map<String, String> getMemInfo();

    /**
     * 获取磁盘信息
     *
     * @return
     */
    List<Map<String, String>> getDiskListInfo();

    /**
     * 获取CPU信息
     *
     * @return
     */
    Map<String, String> getCpuInfo();

}
