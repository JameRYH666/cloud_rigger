package com.jeeadmin.service;

import com.jeeadmin.api.ICloudActivityEnclosureService;
import com.jeeadmin.entity.CloudActivityEnclosure;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.mapper.CloudActivityEnclosureMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Sgz
 * @time: 2020/9/12 11:37
 * @description:
 */
@Service
public class CloudActivityEnclosureServiceImpl extends BaseServiceImpl<CloudActivityEnclosureMapper,
        CloudActivityEnclosure> implements ICloudActivityEnclosureService {

    /**
     * 根据活动id获取附件集合
     *
     * @param activityId
     * @return
     */
    @Override
    public List<CloudEnclosure> findEnclosuresByActivityId(Long activityId) {
        return findEnclosuresByActivityId(activityId);
    }
}
