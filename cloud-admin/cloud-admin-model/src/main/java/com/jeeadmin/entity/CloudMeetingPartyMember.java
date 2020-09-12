package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Seven Lee
 * @description 会议党员关系表
 * @date 2020/9/8
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_meeting_party_member")
public class CloudMeetingPartyMember extends BaseModel<CloudMeetingPartyMember> {
    /**
     * 会议id
     */
    private Long meetingId;

    /**
     * 党员id
     */
    private Long partyMemberId;

    /**
     * 是否为发起人(1:是，2:不是)
     */
    private String promoterFlag;
}