package com.jeeadmin.vo.record;

import com.jeeadmin.entity.CloudRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudRecordVo extends CloudRecord {


    /**
     * 驳回原因，表中不存在字段
     */
    @ApiModelProperty(value = "驳回原因，表中不存在字段")
    private String examineRejectReason;

}
