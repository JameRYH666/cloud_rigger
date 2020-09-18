package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudMeetingPartyMember;
import com.jeeadmin.vo.meeting.CloudMeetingDetailVo;
import com.jeerigger.frame.base.mapper.BaseMapper;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      参加会议党员mapper接口
 * @date 2020/9/8
**/
public interface CloudMeetingPartyMemberMapper extends BaseMapper<CloudMeetingPartyMember> {
    /**
     * @Author: Sgz
     * @Time: 17:50 2020/9/12
     * @Params: [meetingId]
     * @Return: java.util.List<com.jeeadmin.vo.meeting.CloudMeetingDetailVo>
     * @Throws:
     * @Description: 通过会议id查询所有的参会人员
     */
    List<CloudMeetingDetailVo> getAllMeetingMembers(Long meetingId);

    /**
     * 保存参会人员信息
     * @param cloudMeetingPartyMembers
     * @return
     */
    boolean savaMeetingMember( CloudMeetingPartyMember cloudMeetingPartyMember);



}