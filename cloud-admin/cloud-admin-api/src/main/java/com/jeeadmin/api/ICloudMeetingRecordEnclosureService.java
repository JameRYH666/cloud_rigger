package com.jeeadmin.api;


import com.jeeadmin.entity.CloudMeetingRecord;
import com.jeeadmin.entity.CloudMeetingRecordEnclosure;

import com.jeerigger.frame.base.service.BaseService;

import java.util.List;

/**
 * @author: Sgz
 * @time: 2020/9/15 14:58
 * @description:
 *  会议记录附件接口
 */
public interface ICloudMeetingRecordEnclosureService extends BaseService<CloudMeetingRecordEnclosure> {
    /**
     * 查询所有的会议记录附件信息
     * @return
     */
    List<CloudMeetingRecordEnclosure> selectAllMeetingRecordEnclosure();

    /**
     *根据会议记录附件id查询该会议记录附件
     * @return
     */
    CloudMeetingRecordEnclosure selectOne(Long id);

    /**
     * 新增会议记录附件信息
     * @param cloudMeetingRecordEnclosure
     * @return
     */

    boolean saveMeetingRecordEnclosure(CloudMeetingRecordEnclosure cloudMeetingRecordEnclosure);

    /**
     * 根据会议附件id删除会议记录附件
     * @param id
     * @return
     */
    boolean deleteMeetingRecordEnclosure(Long id);

    /**
     * 更新会议记录附件信息
     * @param cloudMeetingRecordEnclosure
     * @return
     */
    boolean updateMeetingRecordEnclosure(CloudMeetingRecordEnclosure cloudMeetingRecordEnclosure);

    /**
     * @Author: Sgz
     * @Time: 9:04 2020/9/18
     * @Params: [meetingRecordId]
     * @Return: java.util.List<com.jeeadmin.entity.CloudMeetingRecordEnclosure>
     * @Throws:
     * @Description:
     *      根据会议记录id查询会议记录附件
     */
    List<CloudMeetingRecordEnclosure> selectMeetingRecordEnclosures(Long meetingRecordId);

}
