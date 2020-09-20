package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Pattern;
import java.util.Date;
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
    @TableField(exist = false)
    private String activityTypeName;
    /**
     * 活动形式
     */
    private String formCode;
    /**
     * 活动形式名称
     */
    @TableField(exist = false)
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
     * 类型外键
     */
    private Long maId;
    /**
     *  活动状态(1：正常，2：删除，3：驳回，4：未审核)
     */
    @Pattern(regexp = "[1234]",message = "活动状态必须为1或者2或者3或者4")
    private String activityStatus;
    /**
     * 附件集合
     */
    @TableField(exist = false)
    private List<CloudEnclosure> cloudEnclosureList;

    private Date createDate;
}