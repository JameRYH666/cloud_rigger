package com.jeeadmin.vo.member;

import com.jeeadmin.entity.CloudOrg;
import com.jeeadmin.entity.CloudPartyMember;
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
    private String orgName;
    private CloudPartyMember cloudPartyMember;

}
