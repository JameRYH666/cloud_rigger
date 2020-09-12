package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudActivityService;
import com.jeeadmin.entity.CloudActivity;
import com.jeeadmin.mapper.CloudActivityMapper;
import com.jeeadmin.vo.activity.QueryActivityVo;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.core.module.sys.util.SysDictUtil;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
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

    /**
     * 获取活动信息
     *
     * @param
     * @param pageHelper
     * @return
     */
    @Override
    public Page<CloudActivity> selectPage(PageHelper<QueryActivityVo> pageHelper) {
        Page<CloudActivity> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudActivity> queryWrapper = new QueryWrapper<CloudActivity>();
        if (pageHelper.getData() != null) {
            QueryActivityVo queryActivityVo = pageHelper.getData();
            // 根据活动地址进行条件查询
            if (StringUtil.isNotEmpty(queryActivityVo.getActivityAddress())) {
                queryWrapper.lambda().like(CloudActivity::getActivityAddress, queryActivityVo.getActivityAddress());
            }
            //根据活动形式进行条件查询
            if (StringUtil.isNotEmpty(queryActivityVo.getFormCode())) {
                queryWrapper.lambda().eq(CloudActivity::getFormCode, queryActivityVo.getFormCode());
            }
            // 根据活动类型进行条件查询
            if (StringUtil.isNotEmpty(queryActivityVo.getActivityCode())) {
                queryWrapper.lambda().eq(CloudActivity::getActivityCode, queryActivityVo.getActivityCode());
            }
        }
        queryWrapper.lambda().orderByAsc(CloudActivity::getActivityTile);
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
        }
        return activity;
    }

    /**
     * 新增活动信息数据
     *
     * @param activity
     * @return
     */
    @Override
    public CloudActivity saveActivity(CloudActivity activity) {
        // 检验活动数据是否存在
        ValidateUtil.validateObject(activity);
        activity.setId(snowFlake.nextId());
        activity.setCreateUser(SecurityUtil.getUserId());
        activity.setCreateDate(new Date());
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
    @Override
    public boolean deleteActivity(Long activityId) {
        CloudActivity oldData = this.getById(activityId);
        if (oldData == null) {
            return true;
        }
        return this.removeById(activityId);
    }


}
