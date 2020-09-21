package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudActivityService;
import com.jeeadmin.api.ICloudEnclosureService;
import com.jeeadmin.api.ICloudExamineService;
import com.jeeadmin.entity.*;
import com.jeeadmin.mapper.CloudActivityEnclosureMapper;
import com.jeeadmin.mapper.CloudActivityMapper;
import com.jeeadmin.mapper.CloudEnclosureMapper;
import com.jeeadmin.vo.activity.CloudActivityVo;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.core.module.sys.util.SysDictUtil;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.MeetingAndActivityEnum;
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

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/9 14:18
 * @Description:
 */
@Service
public class CloudActivityServiceImpl extends BaseServiceImpl<CloudActivityMapper, CloudActivity> implements ICloudActivityService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private com.jeeadmin.api.ICloudActivityEnclosureService cloudActivityEnclosureService;
    @Autowired
    private CloudActivityEnclosureMapper cloudActivityEnclosureMapper;
    @Autowired
    private CloudEnclosureMapper cloudEnclosureMapper;
    @Autowired
    private ICloudEnclosureService cloudEnclosureService;
    @Autowired
    private CloudActivityMapper cloudActivityMapper;
    @Autowired
    private ICloudExamineService cloudExamineService;

    /**
     * 获取活动信息
     * @param
     * @param pageHelper
     * @return
     */
    @Override
    public Page<CloudActivity> selectPage(PageHelper<CloudActivity> pageHelper) {
        Page<CloudActivity> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudActivity> queryWrapper = new QueryWrapper<CloudActivity>();
        if (pageHelper.getData() != null) {
            CloudActivity data = pageHelper.getData();
            // 根据活动地址进行条件查询
            if (StringUtil.isNotEmpty(data.getActivityAddress())) {
                queryWrapper.lambda().like(CloudActivity::getActivityAddress, data.getActivityAddress());
            }
            // 根据活动形式进行条件查询
            if (StringUtil.isNotEmpty(data.getFormCode())) {
                queryWrapper.lambda().eq(CloudActivity::getFormCode, data.getFormCode());
            }
            // 根据活动类型进行条件查询
            if (StringUtil.isNotEmpty(data.getActivityCode())) {
                queryWrapper.lambda().eq(CloudActivity::getActivityCode, data.getActivityCode());
            }
            queryWrapper.lambda().ne(CloudActivity::getActivityStatus,MeetingAndActivityEnum.REMOVE.getCode());
        }
        queryWrapper.lambda().orderByAsc(CloudActivity::getCreateDate);
        this.page(page, queryWrapper);
        page.getRecords().forEach(cloudActivity -> {
            // todo 需要添加字典类型中，活动类型的值。
            cloudActivity.setActivityTypeName(SysDictUtil.getDictLable("", cloudActivity.getActivityCode()));
            // todo 需要添加字典类型中，活动形式的值。
            cloudActivity.setFormName(SysDictUtil.getDictLable("", cloudActivity.getFormCode()));
        });
        return page;
    }

    @Override
    public CloudActivity getById(Serializable id) {
        if (Objects.isNull(id)) {
            throw new ValidateException("活动的Id不能为空");
        }
        CloudActivity activity = super.getById(id);
        if (null == activity) {
            throw new ValidateException("活动数据为空");
        } else {
            // todo 指定活动形式字典类型
            activity.setFormName(SysDictUtil.getDictLable("", activity.getFormCode()));
            // todo 指定活动类型字典类型
            activity.setActivityTypeName(SysDictUtil.getDictLable("", activity.getActivityCode()));
            // 查询附件，填充附件集合
            List<CloudEnclosure> cloudEnclosureList =
                    cloudActivityEnclosureMapper.findEnclosuresByActivityId(activity.getId());
            if (cloudEnclosureList != null && cloudEnclosureList.size() > 0) {
                activity.setCloudEnclosureList(cloudEnclosureList);
            }
        }
        return activity;
    }

    /**
     * 新增活动信息数据
     * @param activity
     * @return
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public CloudActivityVo saveActivity(CloudActivityVo activity) {
        // 检验活动数据是否存在
        ValidateUtil.validateObject(activity);

        CloudActivity cloudActivity = new CloudActivity();
        CloudActivityEnclosure cloudActivityEnclosure = new CloudActivityEnclosure();
        long id = snowFlake.nextId();
        activity.setId(id);
        BeanUtils.copyProperties(activity,cloudActivity);
        BeanUtils.copyProperties(activity,cloudActivityEnclosure);
        // 创建id
        cloudActivityEnclosure.setActivityId(id);
        cloudActivityEnclosure.setEnclosureId(activity.getCloudEnclosureId());
        cloudActivityEnclosureService.saveActivityEnclosure(cloudActivityEnclosure);
        activity.setCreateUser(SecurityUtil.getUserId());
        activity.setCreateDate(new Date());
        //  新增活动为未审核状态
        activity.setActivityStatus(MeetingAndActivityEnum.NOREVIEWED.getCode());
        // 新增审核信息
        CloudExamine cloudExamine = new CloudExamine();
        cloudExamine.setForeignId(activity.getId());
        cloudExamine.setExamineTypeCode("5");
        cloudExamineService.saveExamine(cloudExamine);
        if (this.save(activity)) {
            return activity;
        } else {
            throw new FrameException("新增活动信息数据失败");
        }
    }

    /**
     * 更新活动信息数据
     *
     * @param activity
     * @return
     */
    @Override
    public boolean updateActivity(CloudActivity activity) {
        CloudActivity oldData = this.getById(activity.getId());
        if (oldData == null) {
            throw new ValidateException("更新的活动信息不存在");
        }
        // 通过校验数据是否存在
        ValidateUtil.validateObject(activity);
        activity.setCreateUser(SecurityUtil.getUserId());
        activity.setUpdateDate(new Date());
        return this.updateById(activity);
    }

    /**
     * 删除活动信息数据
     *
     * @param
     * @return
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public boolean deleteActivity(Long id) {
        CloudActivity oldData = this.getById(id);
        if (oldData == null) {
            return true;
        }else {
            // cloudActivityEnclosureMapper.deleteActivityEnclosureByActivityId(id);
            // 通过活动id删除附件信息和活动附件关系信息
            cloudEnclosureMapper.deleteCloudEnclosureByActivityId(id);
        }
        return this.removeById(id);
    }

    /**
    * @Author: Ryh
    * @Description:         修改活动的状态(逻辑删除)
    * @Param: [cloudActivity]
    * @Date: Create in 2020/9/15
    * @Return: com.jeeadmin.entity.CloudActivity
    * @Throws:
    */
    @Override
    public boolean updateStatus(CloudActivity cloudActivity) {
        CloudActivity oldData = this.getById(cloudActivity.getId());
        // 验证活动数据是否存在
        if (null == oldData) {
            throw new ValidateException("该活动数据不存在,不能进行");
        }
        // 执行更新操作
        UpdateWrapper<CloudActivity> updateWrapper = new UpdateWrapper<CloudActivity>();
        updateWrapper.lambda().set(CloudActivity::getActivityStatus, cloudActivity.getActivityStatus());
        updateWrapper.lambda().eq(CloudActivity::getId,cloudActivity.getId());
        boolean update = this.update(new CloudActivity(), updateWrapper);
        return update;
    }

    /**
    * @Author: Ryh
    * @Description:     根据用户ID查询已经发起的活动
    * @Param: [id]
    * @Date: Create in 2020/9/18
    * @Return: com.jeeadmin.vo.activity.CloudActivityVo
    * @Throws:
    */
    @Override
    public List<CloudActivity> selectByUserId(PageHelper<CloudActivity> pageHelper) {

        Long userId = SecurityUtil.getUserId();
        List<CloudActivity> cloudActivity = cloudActivityMapper.selectByUserId(userId);
        if (null == cloudActivity || "".equals(cloudActivity)){
            throw new ValidateException("当前已发起的数据为空");
        }
        return cloudActivity;
    }

    /**
    * @Author: Ryh
    * @Description:
    *           根据用户id查询已经处理的活动
    * @Date: Create in 2020/9/21
    * @Return: java.util.List<com.jeeadmin.entity.CloudActivity>
    * @Throws:
    */
    @Override
    public List<CloudActivity> selectProcessed() {
        Long userId = SecurityUtil.getUserId();
        List<CloudActivity> cloudActivities = cloudActivityMapper.selectProcessed(userId);
        if (null == cloudActivities || "".equals(cloudActivities)){
            throw new ValidateException("当前已处理的数据为空");
        }
        return cloudActivities;
    }


}
