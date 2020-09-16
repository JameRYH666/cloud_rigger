package com.jeeadmin.service;

import com.jeeadmin.api.ICloudMeetingPartyMemberService;
import com.jeeadmin.entity.CloudMeetingPartyMember;
import com.jeeadmin.mapper.CloudMeetingPartyMemberMapper;
import com.jeeadmin.vo.meeting.CloudMeetingDetailVo;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author: Sgz
 * @time: 2020/9/12 17:06
 * @description:
 */
@Service
public class CloudMeetingPartyMemberServiceImpl extends BaseServiceImpl<CloudMeetingPartyMemberMapper,CloudMeetingDetailVo> implements ICloudMeetingPartyMemberService {
    @Autowired
    private CloudMeetingPartyMemberMapper cloudMeetingPartyMemberMapper;
    @Autowired
    private SnowFlake snowFlake;

    /**
     * 通过会议id获取所有的参会人员
     *
     * @param meetingId
     * @return
     */
    @Override
    public List<CloudMeetingDetailVo> getAllMeetingMembers(Long meetingId) {
        if (Objects.isNull(meetingId)){
            throw new ValidateException("会议不能为空");
        }
        List<CloudMeetingDetailVo> allMeetingMembers = cloudMeetingPartyMemberMapper.getAllMeetingMembers(meetingId);
        if (allMeetingMembers.size()>0){
            return allMeetingMembers;
        }

        throw new ValidateException("该会议没有参会人员");
    }

    /**
     * @Author: Sgz
     * @Time: 11:54 2020/9/14
     * @Params: [cloudMeetingPartyMembers]
     * @Return: boolean
     * @Throws:
     * @Description:
     * 添加参会人员信息
     *
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public boolean saveMeetingMember(List<CloudMeetingPartyMember> cloudMeetingPartyMembers) {


        if (cloudMeetingPartyMembers.size()>0){
            for (CloudMeetingPartyMember cloudMeetingPartyMember : cloudMeetingPartyMembers) {
                cloudMeetingPartyMember.setId(snowFlake.nextId());
                boolean b = cloudMeetingPartyMemberMapper.savaMeetingMember(cloudMeetingPartyMember);
                if (!b){
                    throw new ValidateException("参会人员添加失败");
                }
                return true;

            }
        }
        throw new ValidateException("没有获取到参会人员信息");
    }

    /**
     * 根据会议id删除参会人员，也就是当删除会议的时候，同时删除参会人员
     *
     * @param meetingId
     * @return
     */
    @Override
    public boolean deleteMeetingMember(Long meetingId) {
        if (Objects.nonNull(meetingId)){
            boolean b = this.removeById(meetingId);
          return b;
        }
        throw new ValidateException("没有该会议");
    }


}
