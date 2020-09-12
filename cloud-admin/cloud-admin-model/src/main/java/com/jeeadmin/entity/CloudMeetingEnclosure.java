package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Seven Lee
 * @description
 *      会议附件关系表
 * @date 2020/9/8
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_meeting_enclosure")
public class CloudMeetingEnclosure extends BaseModel<CloudMeetingEnclosure> {
    /**
     * 会议id
     */
    private Long meetingId;

    /**
     * 附件id
     */
    private Long enclosureId;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新用户
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateDate;

}