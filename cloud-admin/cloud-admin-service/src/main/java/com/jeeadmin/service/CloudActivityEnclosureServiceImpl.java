package com.jeeadmin.service;

import com.jeeadmin.api.ICloudActivityEnclosureService;
import com.jeeadmin.entity.CloudActivityEnclosure;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.mapper.CloudActivityEnclosureMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: Sgz
 * @time: 2020/9/12 11:37
 * @description:
 */
@Service
public class CloudActivityEnclosureServiceImpl extends BaseServiceImpl<CloudActivityEnclosureMapper,
        CloudActivityEnclosure> implements ICloudActivityEnclosureService {

    @Autowired
    private SnowFlake snowFlake;

    /**
     * 根据活动id获取附件集合
     *
     * @param activityId
     * @return
     */
    /*@Override
    public List<CloudEnclosure> findEnclosuresByActivityId(Long activityId) {
        return findEnclosuresByActivityId(activityId);
    }*/

    /**
     *      新增活动附件关系信息
     * @param cloudActivityEnclosure
     * @return
     */
    @Override
    public CloudActivityEnclosure saveActivityEnclosure(CloudActivityEnclosure cloudActivityEnclosure) {
        ValidateUtil.validateObject(cloudActivityEnclosure);
        cloudActivityEnclosure.setId(snowFlake.nextId());
        cloudActivityEnclosure.setCreateDate(new Date());
        cloudActivityEnclosure.setCreateUser(SecurityUtil.getUserId());
        if (this.save(cloudActivityEnclosure)){
            return cloudActivityEnclosure;
        }else {
            throw new ValidateException("新增活动附件关系失败");
        }
    }

    /**
     *      删除活动附件关系信息
     * @param id
     * @return
     */
    @Override
    public boolean deleteActivityEnclosure(Long id) {
        return false;
    }
}
