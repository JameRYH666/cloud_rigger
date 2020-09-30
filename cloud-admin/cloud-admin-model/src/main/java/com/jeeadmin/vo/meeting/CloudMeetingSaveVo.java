package com.jeeadmin.vo.meeting;

import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.entity.CloudMeeting;
import com.jeeadmin.entity.CloudMeetingPartyMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudMeetingSaveVo extends CloudMeeting{
    private CloudMeeting cloudMeeting;

    /**
     * 参会人员
     */
    @ApiModelProperty(value = "参会人员")
    private List<CloudMeetingPartyMember> joinMember;

    /**
     * 会议发起人
     */
    @ApiModelProperty(value = "会议发起人")
    private List<CloudMeetingPartyMember> meetingSponsor;
    /**
     * 附件信息
     */
    @ApiModelProperty(value = "附件信息")
    private List<CloudEnclosure> enclosureList;
}
