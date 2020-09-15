package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;


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
     *  会议记录状态(1：正常，2：删除，3：驳回，4：未审核)
     */
    @Pattern(regexp = "[1234]",message = "会议记录状态必须为1或者2或者3或者4")
    private String recordStatus;

    /**
     * 会议记录内容
     */
    private String recordComment;

}