package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Seven Lee
 * @description
 *      会议实体
 * @date 2020/9/8
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudMeeting extends BaseModel<CloudMeeting> {

    /**
     * 会议名称
     */
    private String meetingTile;

    /**
     * 会议类型
     */
    private String typeCode;

    /**
     * 会议地址
     */
    private String meetingAddress;

    /**
     * 会议时间
     */
    private Date meetingTime;

    /**
     * 会议形式
     */
    private String formCode;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 会议内容
     */
    private String meetingComment;

}