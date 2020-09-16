package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudActivityRecordService;
import com.jeeadmin.api.ICloudActivityService;
import com.jeeadmin.api.ICloudMeetingRecordService;
import com.jeeadmin.api.ICloudMeetingService;
import com.jeeadmin.entity.CloudActivityRecord;
import com.jeeadmin.entity.CloudMeetingRecord;
import com.jeeadmin.mapper.CloudActivityRecordMapper;
import com.jeeadmin.mapper.CloudMeetingRecordMapper;
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
 * @author sgz
 *  会议记录信息类
 */
@Service
public class CloudMeetingRecordServiceImpl extends BaseServiceImpl<CloudMeetingRecordMapper, CloudMeetingRecord> implements ICloudMeetingRecordService {

    @Autowired
    private ICloudMeetingService cloudMeetingServiceImpl;
    @Autowired
    private SnowFlake snowFlake;
  /**
   * @Author: Sgz
   * @Time: 18:04 2020/9/11
   * @Params: [pageHelper]
   * @Return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jeeadmin.entity.CloudMeetingRecord>
   * @Throws:
   * @Description:
   *    查询所有的会议记录信息 ，并进行分页查询操作
   */
    @Override
    public Page<CloudMeetingRecord> selectData(PageHelper<CloudMeetingRecord> pageHelper) {
        Page<CloudMeetingRecord> page = new Page<CloudMeetingRecord>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudMeetingRecord> queryWrapper = new QueryWrapper<CloudMeetingRecord>();
        if(pageHelper.getData() != null){
            CloudMeetingRecord cloudMeetingData = pageHelper.getData();
            if (StringUtil.isNotEmpty(cloudMeetingData.getRecordTitle())){
                queryWrapper.lambda().like(CloudMeetingRecord::getRecordTitle,cloudMeetingData.getRecordTitle());
            }
        }
        queryWrapper.lambda().orderByAsc(CloudMeetingRecord::getRecordTitle);
        this.page(page,queryWrapper);
        return page;
    }

   /**
    * @Author: Sgz
    * @Time: 18:04 2020/9/11
    * @Params: [record]
    * @Return: com.jeeadmin.entity.CloudMeetingRecord
    * @Throws:
    * @Description:
    *   新增会议记录信息,同时添加会议记录附件信息
    *
    */
    @Override
    public CloudMeetingRecord saveRecord(CloudMeetingRecord record) {

        // 检验活动数据是否存在
        ValidateUtil.validateObject(record);
        record.setId(snowFlake.nextId());
        record.setCreateDate(new Date());
       record.setCreateUser(SecurityUtil.getUserId());
       if (this.save(record)){
           return record;
       }else {
           throw new FrameException("新增记录数据失败");
       }

    }

  /**
   * @Author: Sgz
   * @Time: 18:04 2020/9/11
   * @Params: [record]
   * @Return: boolean
   * @Throws:
   * @Description:
   *    更新会议记录信息
   *
   */
    @Override
    public boolean updateRecord(CloudMeetingRecord record) {
        CloudMeetingRecord oldData = this.getById(record.getId());
        if (oldData == null){
            throw new ValidateException("更新的活动记录数据不存在");
        }
        ValidateUtil.validateObject(record);
        record.setUpdateDate(new Date());
        record.setUpdateUser(SecurityUtil.getUserId());
        return this.updateById(record);
    }

    /**
     * @Author: Sgz
     * @Time: 18:04 2020/9/11
     * @Params: [recordId]
     * @Return: boolean
     * @Throws:
     * @Description:
     *  删除会议记录信息
     *
     */
    @Override
    public boolean deleteRecord(Long recordId) {
        CloudMeetingRecord oldData = this.getById(recordId);
        if (oldData == null){
            return true;
        }else {
                return this.removeById(recordId);
        }
    }

    /**
     * @Author: Sgz
     * @Time: 18:04 2020/9/11
     * @Params: [recordId]
     * @Return: com.jeeadmin.entity.CloudMeetingRecord
     * @Throws:
     * @Description:
     *  根据会议记录id查询会议记录信息
     *
     */
    @Override
    public CloudMeetingRecord selectOneRecord(Long recordId) {
        if(Objects.isNull(recordId)){
            throw new ValidateException("活动记录的ID不能为空");
        }
        CloudMeetingRecord cloudMeetingRecord = this.getById(recordId);
        if (null == cloudMeetingRecord){
            throw new ValidateException("活动记录不存在");
        }
        return cloudMeetingRecord;
    }
}
