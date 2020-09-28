package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ICloudMeetingActiveTypeService;

import com.jeeadmin.entity.CloudMeetingActiveType;

import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.mapper.CloudMeetingActiveTypeMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.MeetingAndActivityEnum;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: Sgz
 * @time: 2020/9/16 9:46
 * @description:
 *     会议活动类型表的实现
 *
 */
@Service
public class CloudMeetingActiveTypeServiceImpl extends BaseServiceImpl<CloudMeetingActiveTypeMapper, CloudMeetingActiveType>
                                                implements ICloudMeetingActiveTypeService {

    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private CloudMeetingActiveTypeMapper cloudMeetingActiveTypeMapper;

    /**
     * 查询所有的会议类型
     *
     * @param cloudMeetingActiveType
     * @return
     */
    @Override
    public List<CloudMeetingActiveType> selectAll(CloudMeetingActiveType cloudMeetingActiveType) {
        QueryWrapper<CloudMeetingActiveType> queryWrapper = new QueryWrapper<>();
       if(Objects.isNull(cloudMeetingActiveType)){
           // 查询所有的会议类型信息，并且状态码=1
           List<CloudMeetingActiveType> cloudMeetingActiveTypes = cloudMeetingActiveTypeMapper.selectAll();
           // 判断是否查询到数据
           if (cloudMeetingActiveTypes.size()>0){
               return cloudMeetingActiveTypes;
           }
           throw new ValidateException("查询不到会议类型信息");
       }
       // 下面是条件查询，根据不同的条件分别查询

       if (Objects.nonNull(cloudMeetingActiveType.getId())){
           // 根据主键id进行查询
           queryWrapper.lambda().eq(CloudMeetingActiveType::getId,cloudMeetingActiveType.getId());
       }
       if (Objects.nonNull(cloudMeetingActiveType.getMaTypeName())){
           // 根据会议类型进行查询
           queryWrapper.lambda().like(CloudMeetingActiveType::getMaTypeName,cloudMeetingActiveType.getMaTypeName());
       }
       if (Objects.nonNull(cloudMeetingActiveType.getMaCode())){
           // 根据会议名称进行查询

           queryWrapper.lambda().like(CloudMeetingActiveType::getMaCode,cloudMeetingActiveType.getMaCode());
       }
       return this.list(queryWrapper);
    }

    /**
     * 新增活动会议类型
     *
     * @param cloudMeetingActiveType
     * @return
     */
    @Override
    public boolean saveMeetingAndActiveType(CloudMeetingActiveType cloudMeetingActiveType) {

        if (Objects.isNull(cloudMeetingActiveType)){

            throw new ValidateException("没有获取到会议活动类型信息");
        }
        // 验证数据的完整性
        ValidateUtil.validateObject(cloudMeetingActiveType);
        // 验证新增会议类型是否已经存在
        validateTypeName(cloudMeetingActiveType);
        cloudMeetingActiveType.setId(snowFlake.nextId())
                .setCreateUser(SecurityUtil.getUserId())
                .setCreateDate(new Date());
        cloudMeetingActiveType.setTypeStatus(MeetingAndActivityEnum.NORMAL.getCode());
        if (this.save(cloudMeetingActiveType)){
            return true;
        }
        throw new ValidateException("会议活动类型信息新增失败");
    }
    /**
     * 验证会议类型名称是否存在
     *
     *
     */
    private void validateTypeName(CloudMeetingActiveType  cloudMeetingActiveType) {
        QueryWrapper<CloudMeetingActiveType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CloudMeetingActiveType::getMaTypeName, cloudMeetingActiveType.getMaTypeName());
        if (Objects.nonNull(cloudMeetingActiveType.getId())) {
            queryWrapper.lambda().ne(CloudMeetingActiveType::getId, cloudMeetingActiveType.getId());
        }
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("会议活动类型已经存在！");
        }
    }

    /**
     * @param cloudMeetingActiveType
     * @Author: Sgz
     * @Time: 9:43 2020/9/16
     * @Params: [cloudMeetingActiveType]
     * @Return: boolean
     * @Throws:
     * @Description: 更新会议活动类型
     */
    @Override
    public boolean updateMeetingAndActiveType(CloudMeetingActiveType cloudMeetingActiveType) {
       if (Objects.isNull(cloudMeetingActiveType)){
           throw new ValidateException("没有获取到会议活动信息");
       }
       ValidateUtil.validateObject(cloudMeetingActiveType);
       if (this.updateById(cloudMeetingActiveType)){
           return true;
       }
       throw new ValidateException("更新会议活动类型失败");
    }

    /**
     * @param id
     * @Author: Sgz
     * @Time: 9:44 2020/9/16
     * @Params: [id]
     * @Return: boolean
     * @Throws:
     * @Description: 根据会议类型id更改会议类型状态，从而实现逻辑删除
     */
    @Override
    public boolean updateMeetingAndActiveTypeStatus(CloudMeetingActiveType cloudMeetingActiveType) {


       if (Objects.isNull(cloudMeetingActiveType)){
           throw new ValidateException("没有获取到会议类型信息");
       }
        CloudMeetingActiveType oldData = this.getById(cloudMeetingActiveType.getId());
       if (Objects.isNull(oldData)){
           throw new ValidateException("该会议活动类型不存在");
       }
        cloudMeetingActiveType.setTypeStatus(MeetingAndActivityEnum.REMOVE.getCode())
                                .setUpdateDate(new Date())
                                .setUpdateUser(SecurityUtil.getUserId());
        if (this.updateById(cloudMeetingActiveType)){
            return true;
        }
        throw new ValidateException("会议活动类型修改失败");
    }
}
