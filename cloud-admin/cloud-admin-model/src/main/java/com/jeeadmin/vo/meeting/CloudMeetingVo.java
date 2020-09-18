package com.jeeadmin.vo.meeting;

import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.entity.CloudMeeting;
import com.jeeadmin.entity.CloudMeetingActiveType;
import com.jeeadmin.entity.CloudMeetingPartyMember;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author Seven Lee
 * @Date Create in 2020/9/12 15:25
 * @Description
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudMeetingVo extends CloudMeeting {

    private String formName;
    private String typeName;
    private String memberName;
    private Date oneCreateDate;
    private List<CloudMeetingPartyMember> cloudMeetingPartyMembers;
    private List<CloudEnclosure> cloudEnclosures;
    private CloudMeetingActiveType cloudMeetingActiveType;
}
