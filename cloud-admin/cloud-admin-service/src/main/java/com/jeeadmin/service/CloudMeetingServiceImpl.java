package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudMeetingService;

import com.jeeadmin.entity.CloudMeeting;

import com.jeeadmin.mapper.CloudMeetingMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author: Sgz
 * @time: 2020/9/11 17:19
 * @description:
 *  会议信息类
 */
@Service
public class CloudMeetingServiceImpl extends BaseServiceImpl<CloudMeetingMapper, CloudMeeting> implements ICloudMeetingService {
    @Autowired
    private SnowFlake snowFlake;
    /**
     * @param pageHelper
     * @Author: Sgz
     * @Time: 16:56 2020/9/11
     * @Params: [pageHelper]
     * @Return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jeeadmin.entity.CloudMeeting>
     * @Throws:
     * @Description: 查询所有的会议信息，并进行分页处理
     */
    @Override
    public Page<CloudMeeting> selectPage(PageHelper<CloudMeeting> pageHelper) {
        Page<CloudMeeting> page = new Page<CloudMeeting>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudMeeting> queryWrapper = new QueryWrapper<>();
        if(pageHelper.getData() != null){
            CloudMeeting meetingData = pageHelper.getData();
            // 根据活动地址进行条件查询
            if(StringUtil.isNotEmpty(meetingData.getMeetingAddress())){
                queryWrapper.lambda().like(CloudMeeting::getMeetingAddress,meetingData.getMeetingAddress());
            }
            //根据活动形式进行条件查询
            if (StringUtil.isNotEmpty(meetingData.getFormCode())){
                queryWrapper.lambda().eq(CloudMeeting::getFormCode,meetingData.getFormCode());
            }
            // 根据活动类型进行条件查询
            if (StringUtil.isNotEmpty(meetingData.getTypeCode())){
                queryWrapper.lambda().eq(CloudMeeting::getTypeCode,meetingData.getTypeCode());
            }
        }
        queryWrapper.lambda().orderByAsc(CloudMeeting::getMeetingTime);
        this.page(page,queryWrapper);
        return page;
    }

    /**
     * @Author: Sgz
     * @Time:  2020/9/11
     * @Params: [id]
     * @Return: com.jeeadmin.entity.CloudMeeting
     * @Throws:
     * @Description:
     *  根据会议id查询会议的详细信息
     *
     */
    @Override
    public CloudMeeting selectOneMeeting(Long id) {
        if(Objects.isNull(id)){
            throw new ValidateException("会议的Id不能为空");
        }
        CloudMeeting meeting = this.getById(id);
        if (null == meeting){
            throw new ValidateException("会议数据为空");
        }
        return meeting;
    }

   /**
    * @Author: Sgz
    * @Time: 17:38 2020/9/11
    * @Params: [meeting]
    * @Return: com.jeeadmin.entity.CloudMeeting
    * @Throws:
    * @Description:
    *   增加会议信息
    *
    */
    @Override
    public CloudMeeting saveMeeting(CloudMeeting meeting) {
        // 检验会议数据是否存在
        ValidateUtil.validateObject(meeting);
        // 由雪花算法生成主键id
        meeting.setId(snowFlake.nextId());
        // 通过安全框架获取到userId
        meeting.setCreateUser(SecurityUtil.getUserId());
        meeting.setCreateDate(new Date());
        if (this.save(meeting)){
            return meeting;
        }else {
            throw new FrameException("新增会议信息数据失败");
        }
    }

    /**
     * @Author: Sgz
     * @Time: 17:38 2020/9/11
     * @Params: [meeting]
     * @Return: boolean
     * @Throws:
     * @Description:
     * 更新会议信息
     *
     */
    @Override
    public boolean updateMeeting(CloudMeeting meeting) {
        // 通过主键获取到会议数据信息
        CloudMeeting oldData = this.getById(meeting.getId());
        // 判断数据是否存在
        if (oldData == null){
            // 如果会议信息不存在 直接抛出异常
            throw new ValidateException("更新的会议信息不存在");
        }
        // 通过校验数据是否存在
        ValidateUtil.validateObject(meeting);
        // 通过security获取到userId，并把数据同步更新到数据库
        meeting.setUpdateUser(SecurityUtil.getUserId());
        meeting.setUpdateDate(new Date());
        return this.updateById(meeting);
    }

   /**
    * @Author: Sgz
    * @Time: 17:38 2020/9/11
    * @Params: [id]
    * @Return: boolean
    * @Throws:
    * @Description:
    * 删除会议信息
    *
    */
    @Override
    public boolean deleteMeeting(Long id) {
        CloudMeeting oldData = this.getById(id);
        if (oldData == null){
            return true;
        }
        return this.removeById(id);
    }

}
