package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Seven Lee
 * @description 会议附件关系表
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
}