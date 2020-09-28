package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudActivity;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.entity.CloudMeeting;
import com.jeeadmin.vo.meeting.CloudMeetingDetailVo;
import com.jeeadmin.vo.meeting.CloudMeetingPartyMemberVo;
import com.jeeadmin.vo.meeting.CloudMeetingVo;
import com.jeeadmin.vo.member.CloudPartyMemberVo;
import com.jeerigger.frame.base.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Seven Lee
 * @description
 *      会议mapper接口
 * @date 2020/9/8
**/
public interface CloudMeetingMapper extends BaseMapper<CloudMeeting> {

    /**
     * @author Seven Lee
     * @description
     *      查询复合会议信息
     * @param
     * @date 2020/9/12
     * @return java.util.List<com.jeeadmin.vo.meeting.CloudMeetingVo>
     * @throws
    **/
    List<CloudMeetingVo> selectAllMeetings(List<Long> ids);

    /**
     * @author Seven Lee
     * @description
     *      查询某个会议详情
     * @param
     * @date 2020/9/12
     * @return java.util.List<com.jeeadmin.vo.meeting.CloudMeetingDetailVo>
     * @throws
    **/
    CloudMeetingDetailVo selectMeetingDetail(Long id);

    /**
     * @author Seven Lee
     * @description
     *      通过会议id查询参与人信息
     * @param
     * @date 2020/9/12
     * @return java.util.List<java.lang.String>
     * @throws
    **/
    List<CloudMeetingPartyMemberVo> selectJoinMembersByMeetingId(Long id);

    /**
     * 查询会议发起人信息
     * @param id
     * @return
     */

    List<CloudMeetingPartyMemberVo> selectMeetingPromotersByMeetingId(Long id);

    /**
     * @author Seven Lee
     * @description
     *      通过会议id查询所有的附件信息
     * @param
     * @date 2020/9/12
     * @return java.util.List<com.jeeadmin.entity.CloudEnclosure>
     * @throws
    **/
    List<CloudEnclosure> selectEnclosuresByMeetingId(Long id);

    /**
     * 新增会议信息
     * @param cloudMeeting
     * @return
     */
    boolean saveOne(CloudMeeting cloudMeeting);

    /**
     * 通过修改状态码实现逻辑删除
     * @param cloudMeeting
     * @return
     */
    boolean updateStatus(CloudMeeting cloudMeeting);

    /**
     *      根据用户ID查询已经发起的活动
     */
    List<CloudMeeting> selectByUserId(Long id);

    /**
     * 根据用户id查询已经处理的会议
     * @param id
     * @return
     */
    List<CloudMeeting> selectMeetingProcessed(Long id);

    /**
     * 根据用户id查询还未处理的会议
     * @param id
     * @return
     */
    List<CloudMeeting> selectMeetingUntreated(Long id);

}