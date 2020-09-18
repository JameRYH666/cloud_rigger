package com.jeeadmin.entity;


import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudNoticeEnclosure extends BaseModel<CloudNoticeEnclosure> {

    /**
     *  通知公告ID
     */
    private Long noticeId;

    /**
     *  附件id
     */
    private Long enclosureId;

    /**
     *  是否图片页面上显示
     */
    private String portalFlag;



}