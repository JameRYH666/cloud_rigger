package com.jeeadmin.vo.member;

import com.jeeadmin.entity.CloudOrg;
import com.jeeadmin.entity.CloudPartyMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author sgz
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudPartyMemberVo extends CloudOrg {
    @ApiModelProperty(value = "组织名称")
    private String orgName;
    @ApiModelProperty(value = "党员信息")
    private CloudPartyMember cloudPartyMember;

}
