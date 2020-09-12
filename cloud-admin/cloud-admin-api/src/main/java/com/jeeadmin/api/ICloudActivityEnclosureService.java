package com.jeeadmin.api;

import com.jeeadmin.entity.CloudActivityEnclosure;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeerigger.frame.base.service.BaseService;

import java.util.List;

/**
 * @Author: Ryh
 * @Description: 活动信息表的实现类
 * @Param: [null]
 * @Date: Create in 2020/9/9
 * @Return: null
 * @Throws:
 */
public interface ICloudActivityEnclosureService extends BaseService<CloudActivityEnclosure> {

    List<CloudEnclosure> findEnclosuresByActivityId(Long activityId);
}
