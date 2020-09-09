package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Seven Lee
 * @description
 *      活动记录实体
 * @date 2020/9/8
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudActivityRecord extends BaseModel<CloudActivityRecord> {

    /**
     * 活动记录标题
     */
    private String recordTitle;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 活动记录内容
     */
    private String recordComment;

}