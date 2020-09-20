package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Seven Lee
 * @description 活动附件关系表
 * @date 2020/9/8
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_activity_party_member")
public class CloudActivityPartyMember extends BaseModel<CloudActivityPartyMember> {
    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 党员id
     */
    private Long partyMemberId;

    /**
     * 是否为发起人
     */
    private String promoterFlag;

    private Date createDate;
}