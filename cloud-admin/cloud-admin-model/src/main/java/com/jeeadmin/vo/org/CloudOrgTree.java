package com.jeeadmin.vo.org;

import com.jeeadmin.entity.CloudOrg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CloudOrgTree extends CloudOrg {

    @ApiModelProperty(value = "子组织机构")
    private List<CloudOrg> orgChilds;
}
