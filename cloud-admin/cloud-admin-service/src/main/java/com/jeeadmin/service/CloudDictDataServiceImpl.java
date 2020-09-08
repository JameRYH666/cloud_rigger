package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ICloudDictDataService;
import com.jeeadmin.api.ICloudDictTypeService;
import com.jeeadmin.entity.CloudDictData;
import com.jeeadmin.entity.CloudDictType;
import com.jeeadmin.mapper.CloudDictDataMapper;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.FlagEnum;
import com.jeerigger.frame.enums.StatusEnum;
import com.jeerigger.frame.enums.UserTypeEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Seven Lee
 * @description
 *      CloudDictDataService
 * @date 2020/9/8
**/
@Service
public class CloudDictDataServiceImpl extends BaseServiceImpl<CloudDictDataMapper, CloudDictData> implements ICloudDictDataService {
    @Autowired
    private ICloudDictTypeService sysDictTypeService;

    @Override
    public List<CloudDictData> selectDictDataList(CloudDictData sysDictData) {
        if (StringUtil.isEmpty(sysDictData.getDictType())) {
            throw new ValidateException("字典类型不能为空！");
        }
        QueryWrapper<CloudDictData> queryWrapper = new QueryWrapper<CloudDictData>();
        queryWrapper.lambda().eq(CloudDictData::getDictType, sysDictData.getDictType());
        //添加字典标签查询条件
        if (StringUtil.isNotEmpty(sysDictData.getDictLabel())) {
            queryWrapper.lambda().like(CloudDictData::getDictLabel, sysDictData.getDictLabel());
        }
        //添加字典键值查询条件
        if (StringUtil.isNotEmpty(sysDictData.getDictValue())) {
            queryWrapper.lambda().like(CloudDictData::getDictValue, sysDictData.getDictValue());
        }
        //添加系统内置查询条件
        if (StringUtil.isNotEmpty(sysDictData.getSysFlag())) {
            queryWrapper.lambda().like(CloudDictData::getSysFlag, sysDictData.getSysFlag());
        }
        //添加状态查询条件
        if (StringUtil.isNotEmpty(sysDictData.getDictStatus())) {
            queryWrapper.lambda().like(CloudDictData::getDictStatus, sysDictData.getDictStatus());
        }
        queryWrapper.lambda().orderByAsc(CloudDictData::getDictSort);
        return this.list(queryWrapper);
    }


    @Override
    public boolean updateStatus(Long dictId, String dictStatus) {
        CloudDictData oldDictData = this.getById(dictId);
        if (oldDictData == null) {
            throw new ValidateException("更新的字典数据不存在！");
        }
        if (oldDictData.getSysFlag().equals(FlagEnum.YES.getCode())) {
            if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                throw new ValidateException("系统内置字典不能进行状态更新！");
            }
        }

        CloudDictData sysDictData = new CloudDictData();
        sysDictData.setId(dictId);
        sysDictData.setDictStatus(dictStatus);
        return this.updateById(sysDictData);

    }

    @Override
    public CloudDictData saveDictData(CloudDictData sysDictData) {
        if (sysDictData.getParentId() == null) {
            sysDictData.setParentId(0L);
        }
        sysDictData.setDictStatus(StatusEnum.NORMAL.getCode());
        sysDictData.setSysFlag(FlagEnum.NO.getCode());

        //校验字典类型是否存在
        validateDictType(sysDictData.getDictType());

        //校验业务数据
        ValidateUtil.validateObject(sysDictData);
        //校验字典数据代码是否已在当前字典类型下存在
        validateDictValue(sysDictData);
        sysDictData.setCreateDate(new Date());
        sysDictData.setUpdateDate(new Date());
        if (this.save(sysDictData)) {
            return sysDictData;
        } else {
            throw new FrameException(ResultCodeEnum.ERROR_SAVE_FAIL, "新增字典数据信息失败！");
        }
    }

    @Override
    public boolean updateDictData(CloudDictData sysDictData) {
        if (sysDictData.getParentId() == null) {
            sysDictData.setParentId(0L);
        }
        CloudDictData oldDictData = this.getById(sysDictData.getId());
        if (oldDictData == null) {
            throw new ValidateException("更新的字典数据不存在！");
        }

        if (oldDictData.getSysFlag().equals(FlagEnum.YES.getCode())) {
            if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                throw new ValidateException("系统内置字典不能进行更新！");
            }
        }

        //校验字典类型是否存在
        validateDictType(sysDictData.getDictType());

        //校验数据
        ValidateUtil.validateObject(sysDictData);
        //校验字典数据代码是否已在当前字典类型下存在
        validateDictValue(sysDictData);

        sysDictData.setUpdateDate(new Date());
        return this.updateById(sysDictData);
    }

    /**
     * 校验字典类型是否存在
     *
     * @param dictType
     */
    private void validateDictType(String dictType) {
        QueryWrapper<CloudDictType> queryWrapper = new QueryWrapper<CloudDictType>();
        queryWrapper.lambda().eq(CloudDictType::getDictType, dictType);
        if (sysDictTypeService.count(queryWrapper) < 1) {
            throw new ValidateException("添加的字典数据类型不存在！");
        }
    }

    /**
     * 校验字典数据代码是否已在当前字典类型下存在
     *
     * @param sysDictData
     */
    private void validateDictValue(CloudDictData sysDictData) {
        QueryWrapper<CloudDictData> queryWrapper = new QueryWrapper();
        if (Objects.nonNull(sysDictData.getId())) {
            queryWrapper.lambda().ne(CloudDictData::getId, sysDictData.getId());
        }
        queryWrapper.lambda().eq(CloudDictData::getDictType, sysDictData.getDictType());
        queryWrapper.lambda().eq(CloudDictData::getDictValue, sysDictData.getDictValue());
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("字典键值已在该字典类型（" + sysDictData.getDictType() + "）下存在！");
        }
    }

    @Override
    public boolean deleteDictData(Long dictId) {
        CloudDictData sysDictData = this.getById(dictId);
        if (sysDictData == null) {
            return true;
        } else {
            if (sysDictData.getSysFlag().equals(FlagEnum.YES.getCode())) {
                if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                    throw new ValidateException("系统内置字典不能进行更新！");
                }
            }
        }
        return this.removeById(dictId);
    }
}
