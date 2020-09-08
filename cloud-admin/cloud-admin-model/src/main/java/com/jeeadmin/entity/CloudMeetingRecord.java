package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author Seven Lee
 * @description
 *      会议记录实体
 * @date 2020/9/8
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudMeetingRecord extends BaseModel<CloudMeetingRecord> {

    /**
     * 会议id
     */
    private Long meetingId;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 会议记录标题
     */
    private String recordTitle;

    /**
     * 会议记录内容
     */
    private String recordComment;

}