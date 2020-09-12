package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudActivity extends BaseModel<CloudActivity> {
    /**
     * 活动标题
     */
    private String activityTile;
    /**
     * 活动类型
     */
    private String activityCode;
    /**
     * 活动类型名称
     */
    private String activityTypeName;
    /**
     * 活动形式
     */
    private String formCode;
    /**
     * 活动形式名称
     */
    private String formName;
    /**
     * 备注信息(冗余字段)
     */
    private String remark;
    /**
     * 活动地址
     */
    private String activityAddress;
    /**
     * 活动内容
     */
    private String activityComment;

    /**
     * 附件集合
     */
    private List<CloudEnclosure> cloudEnclosureList;
}