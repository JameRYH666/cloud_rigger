package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudMeetingEnclosureService;

import com.jeeadmin.entity.CloudMeetingEnclosure;


import com.jeeadmin.entity.CloudMeetingRecordEnclosure;
import com.jeeadmin.mapper.CloudMeetingEnclosureMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: Sgz
 * @time: 2020/9/12 11:37
 * @description:
 */
@Service
public class CloudMeetingEnclosureServiceImpl extends BaseServiceImpl<CloudMeetingEnclosureMapper,CloudMeetingEnclosure> implements ICloudMeetingEnclosureService {
    @Autowired
   private SnowFlake snowFlake;



    /**
     *
     * @Author: Sgz
     * @Time: 10:02 2020/9/12
     * @Params: [pageHelper]
     * @Return: java.util.List<com.jeeadmin.entity.CloudMeetingEnclosure>
     * @Throws:
     * @Description: 查询所有的会议附件信息
     */
    @Override
    public List<CloudMeetingEnclosure> selectAll() {
        QueryWrapper<CloudMeetingEnclosure> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().select();
        List<CloudMeetingEnclosure> list = this.list(queryWrapper);
        if (list.size()>0){
            return list;
        }
        throw new ValidateException("没有会议附件信息");

    }

    /**
     * @param id
     * @Author: Sgz
     * @Time: 10:10 2020/9/12
     * @Params: [id]
     * @Return: com.jeeadmin.entity.CloudMeetingEnclosure
     * @Throws:
     * @Description: 通过会议附件id查询会议附件的详细信息
     */
    @Override
    public CloudMeetingEnclosure getMeetingEnclosureDetailByEnclosureId(Long id) {
       if (Objects.isNull(id)){
            throw new ValidateException("会议附件id不能为空");
       }
       return this.getById(id);
    }

    /**
     * @param cloudMeetingEnclosure
     * @Author: Sgz
     * @Time: 10:21 2020/9/12
     * @Params: [cloudMeetingEnclosure]
     * @Return: com.jeeadmin.entity.CloudMeetingEnclosure
     * @Throws:
     * @Description: 新增会议附件
     */
    @Override
    public boolean saveMeetingEnclosure(CloudMeetingEnclosure cloudMeetingEnclosure) {
        if (Objects.isNull(cloudMeetingEnclosure)){
            throw new ValidateException("会议附件信息不能为空");

        }
        ValidateUtil.validateObject(cloudMeetingEnclosure);

       return this.save(cloudMeetingEnclosure);
    }

    /**
     * @param cloudMeetingEnclosure
     * @Author: Sgz
     * @Time: 10:23 2020/9/12
     * @Params: [cloudMeetingEnclosure]
     * @Return: boolean
     * @Throws:
     * @Description: 修改会议附件信息
     */
    @Override
    public boolean updateMeetingEnclosure(CloudMeetingEnclosure cloudMeetingEnclosure) {
        return false;
    }

    /**
     * @param id
     * @Author: Sgz
     * @Time: 10:24 2020/9/12
     * @Params: [id]
     * @Return: boolean
     * @Throws:
     * @Description: 通过附件id删除附件信息
     */
    @Override
    public boolean deleteMeetingEnclosure(Long id) {
        if (Objects.isNull(id)){
            throw new ValidateException("没有获取到附件信息id");
        }
        return this.removeById(id);
    }

    @Override
    public List<CloudMeetingEnclosure> selectMeetingEnclosuresByMeetingId(Long meetingId) {
        QueryWrapper<CloudMeetingEnclosure> queryWrapper = new QueryWrapper<>();
        if (Objects.isNull(meetingId)){
            throw new ValidateException("没有获取到会议ID");
        }
        queryWrapper.lambda().eq(CloudMeetingEnclosure::getMeetingId,meetingId);
        if (Objects.isNull(queryWrapper)){
            throw new ValidateException("没有会议附件信息");

        }
        return  this.list(queryWrapper);
    }
}
