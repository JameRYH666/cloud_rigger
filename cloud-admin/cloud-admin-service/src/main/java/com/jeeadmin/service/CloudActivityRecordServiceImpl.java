package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudActivityRecordService;
import com.jeeadmin.api.ICloudActivityService;
import com.jeeadmin.entity.CloudActivityRecord;
import com.jeeadmin.mapper.CloudActivityRecordMapper;
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
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/9 18:31
 * @Description:
 */
@Service
public class CloudActivityRecordServiceImpl extends BaseServiceImpl<CloudActivityRecordMapper, CloudActivityRecord> implements ICloudActivityRecordService {

    @Autowired
    private ICloudActivityService cloudActivityService;
    @Autowired
    private SnowFlake snowFlake;
    /****
    * @Author: Ryh
    * @Description:   获取到所有的活动纪录信息
    * @Param: [record]
    * @Date: Create in 2020/9/9
    * @Return: java.util.List<com.jeeadmin.entity.CloudActivityRecord>
    * @Throws:
    */
    @Override
    public Page<CloudActivityRecord> selectData(PageHelper<CloudActivityRecord> pageHelper) {
        Page<CloudActivityRecord> page = new Page<CloudActivityRecord>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudActivityRecord> queryWrapper = new QueryWrapper<CloudActivityRecord>();
        if(pageHelper.getData() != null){
            CloudActivityRecord activityRecordData = pageHelper.getData();
            if (StringUtil.isNotEmpty(activityRecordData.getRecordTitle())){
                queryWrapper.lambda().like(CloudActivityRecord::getRecordTitle,activityRecordData.getRecordTitle());
            }
        }
        queryWrapper.lambda().orderByAsc(CloudActivityRecord::getRecordTitle);
        this.page(page,queryWrapper);
        return page;
    }

    /**
    * @Author: Ryh
    * @Description:     新增活动记录
    * @Param: [record]
    * @Date: Create in 2020/9/10
    * @Return: com.jeeadmin.entity.CloudActivityRecord
    * @Throws:
    */
    @Override
    public CloudActivityRecord saveRecord(CloudActivityRecord record) {
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
    * @Author: Ryh
    * @Description:     修改活动记录数据
    * @Param: [record]
    * @Date: Create in 2020/9/10
    * @Return: boolean
    * @Throws:
    */
    @Override
    public boolean updateRecord(CloudActivityRecord record) {
        CloudActivityRecord oldData = this.getById(record.getId());
        if (oldData == null){
            throw new ValidateException("更新的活动记录数据不存在");
        }
        ValidateUtil.validateObject(record);
        record.setUpdateDate(new Date());
        record.setUpdateUser(SecurityUtil.getUserId());
        return this.updateById(record);
    }

    /**
    * @Author: Ryh
    * @Description:         删除活动记录数据
    * @Param: [recordId]
    * @Date: Create in 2020/9/10
    * @Return: boolean
    * @Throws:
    */
    @Override
    public boolean deleteRecord(Long recordId) {
        CloudActivityRecord oldData = this.getById(recordId);
        if (oldData == null){
            return true;
        }else {
                return this.removeById(recordId);
        }
    }

    /**
    * @Author: Ryh
    * @Description:       查询单个的活动记录信息
    * @Param: [record]
    * @Date: Create in 2020/9/10
    * @Return: java.util.List<com.jeeadmin.entity.CloudActivityRecord>
    * @Throws:
     * @param recordId
    */
    @Override
    public CloudActivityRecord selectOneRecord(Long recordId) {
        if(Objects.isNull(recordId)){
            throw new ValidateException("活动记录的ID不能为空");
        }
        CloudActivityRecord cloudActivityRecord = this.getById(recordId);
        if (null == cloudActivityRecord){
            throw new ValidateException("活动记录不存在");
        }
        return cloudActivityRecord;
    }
}
