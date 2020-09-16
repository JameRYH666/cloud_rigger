package com.jeeadmin.service;


import com.jeeadmin.api.ICloudEnclosureSerivce;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.mapper.CloudEnclosureMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Company Sgz
 * @Author sgz
 * @Date Create in 2020/9/13 23:47
 * @Description:
 */
@Service
public class CloudEnclosureServiceImpl extends BaseServiceImpl<CloudEnclosureMapper, CloudEnclosure> implements ICloudEnclosureSerivce {
    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private CloudEnclosureMapper cloudEnclosureMapper;

    /**

     * @Author: sgz
     * @Description: 删除附件信息
     * @Param: [id]
     * @Date: Create in 2020/9/13
     * @Return: boolean
     * @Throws:
     */
    @Override
    public boolean deleteEnclosure(Long meetingId) {
        if (Objects.nonNull(meetingId)){
           return cloudEnclosureMapper.deleteEnclosure(meetingId);
        }
        throw new ValidateException("附件不能为空");
    }

    /**
     * @param cloudEnclosure
     * @Author: Sgz
     * @Time: 15:19 2020/9/14
     * @Params: [cloudEnclosure]
     * @Return: boolean
     * @Throws:
     * @Description: 新增附件信息
     */
    @Override
    public boolean saveEnclosure(CloudEnclosure cloudEnclosure) {
        cloudEnclosure.setId(snowFlake.nextId());
        if (Objects.nonNull(cloudEnclosure)){
           return this.save(cloudEnclosure);
        }
        throw  new ValidateException("附件信息不能为空");
    }
public class CloudEnclosureServiceImpl extends BaseServiceImpl<CloudEnclosureMapper, CloudEnclosure> implements ICloudEnclosure {

   @Autowired
   private CloudEnclosureMapper cloudEnclosureMapper;

   /* @Override
    public boolean deleteEnclosure(Long id) {
        CloudEnclosure oldData = this.getById(id);
        if(null == oldData){
            return true;
        }else{
            return cloudEnclosureMapper.deleteCloudEnclosureByActivityRecordId(id);
        }
    }*/



}
