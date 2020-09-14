package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudActivityRecord;
import com.jeeadmin.vo.activity.CloudActivityRecordVo;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

/**
* @Author: Ryh
* @Description:     活动记录表 服务类
* @Param: [null]
* @Date: Create in 2020/9/9
* @Return: null
* @Throws:
*/
public interface ICloudActivityRecordService extends BaseService<CloudActivityRecord> {

    /**
     *      获取所有活动记录信息
     * @param
     * @return
     */
    Page<CloudActivityRecord> selectData(PageHelper<CloudActivityRecord> pageHelper);


    /**
     *      新增活动记录信息
     */
    CloudActivityRecordVo saveRecord(CloudActivityRecordVo record);


    /**
     *      更新活动记录信息
     */
    boolean updateRecord(CloudActivityRecordVo record);

    /**
     *      删除活动记录信息
     */
    boolean deleteRecord(Long recordId);

    /**
     *      获取单个活动记录的详情
     * @param id
     * @return
     */
    CloudActivityRecordVo selectOneRecord(Long id);








}
