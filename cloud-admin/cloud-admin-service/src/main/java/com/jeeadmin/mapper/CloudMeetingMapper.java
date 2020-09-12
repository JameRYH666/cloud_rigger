package com.jeeadmin.mapper;

import com.jeeadmin.vo.meeting.CloudMeetingVo;
import com.jeerigger.frame.base.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Seven Lee
 * @description
 *      会议mapper接口
 * @date 2020/9/8
**/
public interface CloudMeetingMapper extends BaseMapper<CloudMeetingVo> {

    List<CloudMeetingVo> selectAllMeetings(List<Long> ids);

}