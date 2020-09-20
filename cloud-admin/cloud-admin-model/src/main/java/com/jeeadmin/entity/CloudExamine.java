package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CloudExamine extends BaseModel<CloudExamine> {


    /**
     * 审核状态(1：正常，2：删除，3：驳回，4：未审核)
     */

    private String examineStatus;

    /**
     * 审核类型(需要查询字典表)
     */

    private String examineTypeCode;

    /**
     * 外键id(根据type_code指定查询某张表)
     */

    private Long foreignId;


    /**
     * 驳回原因
     */

    private String examineRejectReason;

    private Date createDate;

  private String remark;
}