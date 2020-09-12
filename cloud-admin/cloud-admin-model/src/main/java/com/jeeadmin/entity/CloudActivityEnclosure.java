package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Seven Lee
 * @description 活动附件关系表
 * @date 2020/9/8
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_activity_enclosure")
public class CloudActivityEnclosure extends BaseModel<CloudActivityEnclosure> {
    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 附件id
     */
    private Long enclosureId;
}