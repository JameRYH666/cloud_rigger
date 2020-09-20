package com.jeeadmin.api;

import com.jeeadmin.entity.CloudActivityRecordEnclosure;
import com.jeerigger.frame.base.service.BaseService;

public interface ICloudActivityRecordEnclosureService extends BaseService<CloudActivityRecordEnclosure> {


    /**
     *      新增活动记录和附件的关系的信息
     */
    CloudActivityRecordEnclosure saveRecordEnclosure(CloudActivityRecordEnclosure cloudActivityRecordEnclosure);


    /**
    * @Author: Ryh
    * @Description:   通过活动记录ID删除活动记录和附件的关系
    * @Param: [null]
    * @Date: Create in 2020/9/13
    * @Return: null
    * @Throws:
    */
    boolean deleteRecordEnclosureByActivityRecordId(Long id);



}
