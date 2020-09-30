package com.jeeadmin.vo.org;


import com.jeeadmin.entity.CloudOrg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;



@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CloudOrgVo extends CloudOrg {
    @ApiModelProperty(value = "数量")
    private Integer count;
    @ApiModelProperty(value = "组织机构领导")
    private String orgLeader;
    @ApiModelProperty(value = "电话号")
    private Long phoneNumer;
}
