package com.jeeadmin.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudDictDataService;
import com.jeeadmin.api.ICloudDictTypeService;
import com.jeeadmin.entity.CloudDictData;
import com.jeeadmin.entity.CloudDictType;
import com.jeeadmin.mapper.CloudDictTypeMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.FlagEnum;
import com.jeerigger.frame.enums.UserTypeEnum;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Seven Lee
 * @description
 *      字典类型表 服务实现类
 * @date 2020/9/9
**/
@Service
public class CloudDictTypeServiceImpl extends BaseServiceImpl<CloudDictTypeMapper, CloudDictType> implements ICloudDictTypeService {
    @Autowired
    private ICloudDictDataService sysDictDataService;

    @Override
    public Page<CloudDictType> selectPage(PageHelper<CloudDictType> pageHelper) {
        Page page = new Page<CloudDictType>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudDictType> queryWrapper = new QueryWrapper<>();
        if (pageHelper.getData() != null) {
            CloudDictType sysDictType = pageHelper.getData();
            //字典名称模糊查询
            if (StringUtil.isNotEmpty(sysDictType.getDictName())) {
                queryWrapper.lambda().like(CloudDictType::getDictName, sysDictType.getDictName());
            }
            //字典类型模糊查询
            if (StringUtil.isNotEmpty(sysDictType.getDictType())) {
                queryWrapper.lambda().like(CloudDictType::getDictType, sysDictType.getDictType());
            }
            //是否为系统默认
            if (StringUtil.isNotEmpty(sysDictType.getSysFlag())) {
                queryWrapper.lambda().eq(CloudDictType::getSysFlag, sysDictType.getSysFlag());
            }
            //状态
            if (StringUtil.isNotEmpty(sysDictType.getDictStatus())) {
                queryWrapper.lambda().eq(CloudDictType::getDictStatus, sysDictType.getDictStatus());
            }

        }
        return (Page<CloudDictType>) this.page(page, queryWrapper);
    }

    @Override
    public boolean updateStatus(CloudDictType sysDictType) {
        CloudDictType oldDictType = this.getById(sysDictType.getId());
        //验证字典类型是否存在
        if (oldDictType == null) {
            throw new ValidateException("该字典类型不存在不能进行更新！");
        }
        if (oldDictType.getSysFlag().equals(FlagEnum.YES.getCode())) {
            if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SYSTEM_ADMIN_USER)) {
                throw new ValidateException("系统内置字典不能进行状态更新！");
            }
        }
        //执行字典类型状态更新
        UpdateWrapper<CloudDictType> updateWrapper = new UpdateWrapper<CloudDictType>();
        updateWrapper.lambda().set(CloudDictType::getDictStatus, sysDictType.getDictStatus());
        updateWrapper.lambda().eq(CloudDictType::getId, sysDictType.getId());

        boolean flag = this.update(new CloudDictType(), updateWrapper);
        if (flag) {
            UpdateWrapper<CloudDictData> updateDataWrapper = new UpdateWrapper<>();
            updateDataWrapper.lambda().set(CloudDictData::getDictStatus, sysDictType.getDictStatus());
            updateDataWrapper.lambda().eq(CloudDictData::getDictType, oldDictType.getDictType());
            sysDictDataService.update(new CloudDictData(), updateDataWrapper);
        }
        return flag;
    }


    @Override
    public boolean saveDictType(CloudDictType sysDictType) {
        ValidateUtil.validateObject(sysDictType);
        validateDictType(sysDictType);
        return this.save(sysDictType);
    }

    @Override
    public boolean updateDictType(CloudDictType sysDictType) {
        CloudDictType oldDictType = this.getById(sysDictType.getId());
        if (oldDictType == null) {
            throw new ValidateException("该字典类型（" + sysDictType.getDictType() + "）不存在不能进行更新！");
        }
        if (oldDictType.getSysFlag().equals(FlagEnum.YES.getCode())) {
            if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SYSTEM_ADMIN_USER)) {
                throw new ValidateException("系统内置字典不能进行更新！");
            }
        }

        ValidateUtil.validateObject(sysDictType);
        validateDictType(sysDictType);
        boolean flag = this.updateById(sysDictType);
        if (flag && !oldDictType.getDictType().equals(sysDictType.getDictType())) {
            UpdateWrapper<CloudDictData> updateWrapper = new UpdateWrapper();
            updateWrapper.lambda().set(CloudDictData::getDictType, sysDictType.getDictType());
            updateWrapper.lambda().eq(CloudDictData::getDictType, oldDictType.getDictType());
            sysDictDataService.update(new CloudDictData(), updateWrapper);
        }
        return flag;
    }

    /**
     * 添加或更新校验字典类型是否已存在
     *
     * @param sysDictType
     */
    private void validateDictType(CloudDictType sysDictType) {
        QueryWrapper<CloudDictType> queryWrapper = new QueryWrapper<CloudDictType>();
        if (Objects.nonNull(sysDictType.getId())) {
            queryWrapper.lambda().ne(CloudDictType::getId, sysDictType.getId());
        }
        queryWrapper.lambda().eq(CloudDictType::getDictType, sysDictType.getDictType());
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("字典类型（" + sysDictType.getDictType() + "）已存在！");
        }
    }

    @Override
    public boolean deleteDictType(Long dictId) {
        CloudDictType sysDictType = this.getById(dictId);
        if (sysDictType == null) {
            return true;
        } else {
            if (sysDictType.getSysFlag().equals(FlagEnum.YES.getCode())) {
                if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SYSTEM_ADMIN_USER)) {
                    throw new ValidateException("系统内置字典不能进行删除！");
                }
            }
        }
        QueryWrapper<CloudDictData> whereWrapper = new QueryWrapper<CloudDictData>();
        whereWrapper.lambda().eq(CloudDictData::getDictType, sysDictType.getDictType());
        //删除字典数据
        if (sysDictDataService.remove(whereWrapper)) {
            //删除字典类型
            if (this.removeById(dictId)) {
                return true;
            }
        }
        return false;
    }
}
