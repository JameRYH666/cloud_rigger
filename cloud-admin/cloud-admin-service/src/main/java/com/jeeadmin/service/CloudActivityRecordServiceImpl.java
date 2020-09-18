package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudActivityRecordEnclosure;
import com.jeeadmin.api.ICloudActivityRecordService;
import com.jeeadmin.api.ICloudActivityService;

import com.jeeadmin.entity.CloudActivityRecord;
import com.jeeadmin.entity.CloudActivityRecordEnclosure;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.mapper.CloudActivityRecordEnclosureMapper;
import com.jeeadmin.mapper.CloudActivityRecordMapper;
import com.jeeadmin.mapper.CloudEnclosureMapper;
import com.jeeadmin.vo.activity.CloudActivityRecordVo;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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
    @Autowired
    private CloudActivityRecordMapper cloudActivityRecordMapper;
    @Autowired
    private ICloudActivityRecordEnclosure cloudActivityRecordEnclosure;
    @Autowired
    private CloudActivityRecordEnclosureMapper cloudActivityRecordEnclosureMapper;
    @Autowired
    private CloudEnclosureMapper cloudEnclosureMapper;

    /**
    * @Author: Ryh
    * @Description:   获取到所有的活动纪录信息
    * @Param: [record]
    * @Date: Create in 2020/9/9
    * @Return: java.util.List<com.jeeadmin.entity.CloudActivityRecord>
    * @Throws:
     * @return
     * @param pageHelper
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
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public CloudActivityRecordVo saveRecord(CloudActivityRecordVo record) {
        // 检验活动数据是否存在
        ValidateUtil.validateObject(record);
        // 创建对象，把vo里的数据复制到实体中
        CloudActivityRecord cloudActivityRecord = new CloudActivityRecord();
        CloudActivityRecordEnclosure cloudActivityRecordEnclosure1 = new CloudActivityRecordEnclosure();
        long id = snowFlake.nextId();
        record.setId(id);
        BeanUtils.copyProperties(record,cloudActivityRecord);
        BeanUtils.copyProperties(record,cloudActivityRecordEnclosure1);
        // 创建id
        cloudActivityRecordEnclosure1.setEnclosureId(record.getCloudEnclosureId());
        cloudActivityRecordEnclosure1.setActivityRecordId(id);

        cloudActivityRecordEnclosure1.setCreateUser(SecurityUtil.getUserId());
        cloudActivityRecordEnclosure.saveRecordEnclosure(cloudActivityRecordEnclosure1);
        cloudActivityRecord.setCreateUser(SecurityUtil.getUserId());
       if (this.save(cloudActivityRecord)){
           return record;
       }else {
           throw new FrameException("新增活动记录数据失败");
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
    public boolean updateRecord(CloudActivityRecordVo record) {
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
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public boolean deleteRecord(Long recordId) {
        CloudActivityRecord oldData = this.getById(recordId);
        if (oldData == null ){
            return true;
        }else {
            // 调用通过活动记录id删除活动记录和附件关系的信息
           // cloudActivityRecordEnclosureMapper.deleteRecordEnclosureByActivityRecordId(recordId);
            // 调用通过活动记录id删除附件信息和活动附件关系信息
            cloudEnclosureMapper.deleteCloudEnclosureByActivityRecordId(recordId);
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
     * @param
    */
    @Override
    public CloudActivityRecordVo selectOneRecord(Long id) {
        if(Objects.isNull(id)){
            throw new ValidateException("活动记录的ID不能为空");
        }
//        CloudActivityRecord cloudActivityRecord = this.getById(id);
//        if (null == cloudActivityRecord){
//            throw new ValidateException("活动记录不存在");
//        }
        // 查询单个活动记录的详情
        CloudActivityRecordVo cloudActivityRecordVo = cloudActivityRecordMapper.selectActivityRecordDetail(id);
        // 查询活动记录的附件(所有附件中的活动记录信息ID时一样的)
        List<CloudEnclosure> cloudEnclosures = cloudActivityRecordMapper.selectEnclosuresByActivityRecordId(id);
        if (null == cloudEnclosures || "".equals(cloudEnclosures)){
            throw new ValidateException("活动记录附件未上传");
        }
        if (cloudEnclosures.size() > 0){
            cloudActivityRecordVo.setEnclosureList(cloudEnclosures);
        }
        String loginName = SecurityUtil.getUser().getLoginName();
        cloudActivityRecordVo.setCreateName(loginName);
        if (null == cloudActivityRecordVo){
            throw new ValidateException("活动记录数据为空");
        }
        return cloudActivityRecordVo;
    }
}
