package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CloudMeetingActiveType extends BaseModel<CloudMeetingActiveType> {


    /**
     * 会议/活动类型名称
     */

    private String maTypeName;

    /**
     * 分类名称(会议或者活动)
     */

    private String maCode;

    /**
     * 举办次数
     */

    private Integer maTimes;

    /**
     * 间隔单位
     */
    @TableField("`ma_interval_ unit_code`")
    private String maIntervalUnitCode;

    /**
     * 间隔周期
     */

    private Integer maIntervalCycle;


    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 类型状态(1：正常，2：删除)
     */

    private String typeStatus;


    private Date createDate;



}