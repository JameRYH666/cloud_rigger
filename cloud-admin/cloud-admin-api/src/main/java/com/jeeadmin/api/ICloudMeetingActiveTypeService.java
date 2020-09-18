package com.jeeadmin.api;


import com.jeeadmin.entity.CloudMeetingActiveType;
import com.jeerigger.frame.base.service.BaseService;

import java.util.List;

/**
 * @author: Sgz
 * @time: 2020/9/16 9:37
 * @description:
 */
public interface ICloudMeetingActiveTypeService extends BaseService<CloudMeetingActiveType> {
    /**
     * 查询所有的会议类型
     * @param cloudMeetingActiveType
     * @return
     */
    List<CloudMeetingActiveType> selectAll(CloudMeetingActiveType cloudMeetingActiveType);

    /**
     * 新增活动会议类型
     * @param cloudMeetingActiveType
     * @return
     */
    boolean saveMeetingAndActiveType(CloudMeetingActiveType cloudMeetingActiveType);

    /**
     * @Author: Sgz
     * @Time: 9:43 2020/9/16
     * @Params: [cloudMeetingActiveType]
     * @Return: boolean
     * @Throws:
     * @Description:
     *      更新会议活动类型
     */
    boolean updateMeetingAndActiveType(CloudMeetingActiveType cloudMeetingActiveType);

    /**
     * @Author: Sgz
     * @Time: 9:44 2020/9/16
     * @Params: [id]
     * @Return: boolean
     * @Throws:
     * @Description:
     *  根据会议类型id更改会议类型状态，从而实现逻辑删除
     *
     */

    boolean updateMeetingAndActiveTypeStatus(CloudMeetingActiveType cloudMeetingActiveType);
}
