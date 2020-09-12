package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudActivityRecord;
import com.jeeadmin.entity.CloudMeetingRecord;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;
import org.springframework.stereotype.Service;


/**
 * @author sgz
 *  会议信息记录表
 */

public interface ICloudMeetingRecordService extends BaseService<CloudMeetingRecord> {

    /**
     *      获取所有活动记录信息
     * @param
     * @return
     */
    Page<CloudMeetingRecord> selectData(PageHelper<CloudMeetingRecord> pageHelper);


    /**
     *   新增活动记录信息
     */
    CloudMeetingRecord saveRecord(CloudMeetingRecord record);


    /**
     *  更新活动记录信息
     */
    boolean updateRecord(CloudMeetingRecord record);

    /**
     *   删除活动记录信息
     */
    boolean deleteRecord(Long recordId);

    /**
     *  根据
     */

    CloudMeetingRecord selectOneRecord(Long recordId);








}
