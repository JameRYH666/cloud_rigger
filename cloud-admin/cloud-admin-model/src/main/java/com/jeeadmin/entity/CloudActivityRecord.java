package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
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
     *  活动记录状态(1：正常，2：删除，3：驳回，4：未审核)
     */
    @Pattern(regexp = "[1234]",message = "活动记录状态必须为1或者2或者3或者4")
    private String recordStatus;
    /**
     * 活动记录内容
     */
    private String recordComment;

}