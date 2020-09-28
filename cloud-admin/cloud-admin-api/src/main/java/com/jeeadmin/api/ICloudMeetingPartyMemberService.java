package com.jeeadmin.api;

import com.jeeadmin.entity.CloudMeetingPartyMember;
import com.jeeadmin.vo.meeting.CloudMeetingDetailVo;

import com.jeerigger.frame.base.service.BaseService;


import java.util.List;

/**
 * @author: Sgz
 * @time: 2020/9/12 9:58
 * @description:
 * 会议参会人员表接口
 */
public interface ICloudMeetingPartyMemberService extends BaseService<CloudMeetingPartyMember> {
    /**
     * 通过会议id获取所有的参会人员
     * @param meetingId
     * @return
     */
    List<CloudMeetingDetailVo> getAllMeetingMembers(Long meetingId);

    /**
     * 保存参会人员信息
     * @param cloudMeetingPartyMembers
     * @return
     */
    boolean saveMeetingMember(CloudMeetingPartyMember cloudMeetingPartyMember);

    /**
     * 根据会议id删除参会人员，也就是当删除会议的时候，同时删除参会人员
     * @param meetingId
     * @return
     */
    boolean deleteMeetingMember(Long meetingId);





}
