package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Seven Lee
 * @description 会议记录附件关系表
 * @date 2020/9/8
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class CloudMeetingRecordEnclosure extends BaseModel<CloudMeetingRecordEnclosure> {
    /**
     * 会议记录id
     */
    private Long meetingRecordId;

    /**
     * 附件id
     */
    private Long enclosureId;
}