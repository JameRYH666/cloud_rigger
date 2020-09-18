package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudMeetingActiveType;
import com.jeerigger.frame.base.mapper.BaseMapper;

import java.util.List;

/**
 * @author: Sgz
 * @time: 2020/9/16 9:23
 * @description:
 *  会议活动的类型表
 */
public interface CloudMeetingActiveTypeMapper extends BaseMapper<CloudMeetingActiveType> {
    List<CloudMeetingActiveType> selectAll();
}
