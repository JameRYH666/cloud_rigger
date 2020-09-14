package com.jeeadmin.service;

import com.jeeadmin.api.ICloudActivityRecordEnclosure;
import com.jeeadmin.entity.CloudActivityRecordEnclosure;
import com.jeeadmin.mapper.CloudActivityRecordEnclosureMapper;
import com.jeeadmin.mapper.CloudActivityRecordMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.Date;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/13 22:35
 * @Description:
 */
@Service
public class CloudActivityRecordEnclosureImpl extends BaseServiceImpl<CloudActivityRecordEnclosureMapper, CloudActivityRecordEnclosure> implements ICloudActivityRecordEnclosure {


    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private  CloudActivityRecordEnclosureMapper cloudActivityRecordEnclosureMapper;

    /**
     *   新增活动记录附件关系信息
     * @param cloudActivityRecordEnclosure
     * @return
     */
    @Override
    public CloudActivityRecordEnclosure saveRecordEnclosure(CloudActivityRecordEnclosure cloudActivityRecordEnclosure) {
        ValidateUtil.validateObject(cloudActivityRecordEnclosure);
        cloudActivityRecordEnclosure.setId(snowFlake.nextId());
        cloudActivityRecordEnclosure.setCreateUser(SecurityUtil.getUserId());
        cloudActivityRecordEnclosure.setCreateDate(new Date());
        if (this.save(cloudActivityRecordEnclosure)){
            return cloudActivityRecordEnclosure;
        }else {
            throw new ValidateException("新增活动记录附件信息失败");
        }

    }

    /**
    * @Author: Ryh
    * @Description:   通过活动记录ID删除活动记录附件关系
    * @Param: [id]
    * @Date: Create in 2020/9/14
    * @Return: boolean
    * @Throws:
    */
    @Override
    public boolean deleteRecordEnclosureByActivityRecordId(Long id) {

        CloudActivityRecordEnclosure oldData = this.getById(id);
        if (null == oldData){
            return true;
        }else {
            return cloudActivityRecordEnclosureMapper.deleteRecordEnclosureByActivityRecordId(id);
        }
    }






}
