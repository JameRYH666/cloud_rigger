package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jeeadmin.vo.record.CloudRecordVo;
import com.jeerigger.frame.base.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("审核表")
public class CloudExamine extends BaseModel<CloudExamine> {


    /**
     * 审核状态(1：正常，2：删除，3：驳回，4：未审核)
     */
    @ApiModelProperty("审核状态(1：正常，2：删除，3：驳回，4：未审核)")
    private String examineStatus;

    /**
     * 审核类型(需要查询字典表)
     */
    @ApiModelProperty("审核类型(需要查询字典表)")
    private String examineTypeCode;

    /**
     * 外键id(根据type_code指定查询某张表)
     */
    @ApiModelProperty("外键id(根据type_code指定查询某张表)")
    private Long foreignId;


    /**
     * 驳回原因
     */
    @ApiModelProperty("驳回原因")
    private String examineRejectReason;

    /**
     * 备注信息(冗余字段)
     */
    @ApiModelProperty("备注信息(冗余字段)")
    private String remark;

    @TableField(exist = false)
    List<CloudRecordVo> recordList;
}