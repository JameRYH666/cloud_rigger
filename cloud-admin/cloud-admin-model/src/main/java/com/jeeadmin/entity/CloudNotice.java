package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudNotice extends BaseModel<CloudNotice> {

    /**
     *  通知公告的标题
     */
    private String noticeTitle;

    /**
     *  上传通过的党员ID
     */
    private Long partyMemberId;

    /**
     *  备注冗余字段
     */
    private String remark;

    /**
     *  通知公告状态
     */
    private String noticeStatus;

    /**
     *  通知公告内容
     */
    private String noticeContent;


}