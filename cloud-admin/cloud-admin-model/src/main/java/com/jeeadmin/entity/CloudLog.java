package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Seven Lee
 * @description
 *      日志实体
 * @date 2020/9/8
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudLog extends BaseModel<CloudLog> {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户登陆名
     */
    private String loginName;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 日志标题
     */
    private String logTitle;

    /**
     * 请求地址
     */
    private String requestUri;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 远程地址
     */
    private String remoteAddr;

    /**
     * 异常标识
     */
    private String exceptionFlag;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 服务器名称
     */
    private String deviceName;

    /**
     * 浏览器名称
     */
    private String browerName;

    /**
     * 提交时间
     */
    private Date executeTime;

    /**
     * 备注信息(冗余信息)
     */
    private String remark;

    private Date createDate;
}