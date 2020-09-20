package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Seven Lee
 * @description 活动记录附件关系表
 * @date 2020/9/8
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_activity_record_enclosure")
public class CloudActivityRecordEnclosure extends BaseModel<CloudActivityRecordEnclosure> {
    /**
     * 活动记录id
     */
    private Long activityRecordId;

    /**
     * 附件id
     */
    private Long enclosureId;

    private Date createDate;
}