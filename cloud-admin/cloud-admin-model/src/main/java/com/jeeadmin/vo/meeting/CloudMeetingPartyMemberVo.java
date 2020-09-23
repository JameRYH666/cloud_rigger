package com.jeeadmin.vo.meeting;

import com.jeeadmin.entity.CloudMeetingPartyMember;
import com.jeeadmin.entity.CloudPartyMember;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CloudMeetingPartyMemberVo extends CloudPartyMember {
    private CloudMeetingPartyMember cloudMeetingPartyMember;
    private CloudPartyMember cloudPartyMember;
}
