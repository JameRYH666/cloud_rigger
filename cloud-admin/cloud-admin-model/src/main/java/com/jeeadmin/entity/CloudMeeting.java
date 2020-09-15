package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
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
     * 类型外键
     */
    private Long maId;
    /**
     *  会议状态(1：正常，2：删除，3：驳回，4：未审核)
     */
    @Pattern(regexp = "[1234]",message = "会议状态必须为1或者2或者3或者4")
    private String meetingStatus;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 会议内容
     */
    private String meetingComment;

}