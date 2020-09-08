package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudParamService;
import com.jeeadmin.entity.CloudParam;
import com.jeeadmin.mapper.CloudParamMapper;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.FlagEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Seven Lee
 * @description
 *      系统参数配置表 服务实现类
 * @date 2020/9/9
**/
@Service
public class CloudParamServiceImpl extends BaseServiceImpl<CloudParamMapper, CloudParam> implements ICloudParamService {

    @Override
    public Page<CloudParam> selectPage(PageHelper<CloudParam> pageHelper) {
        Page<CloudParam> page = new Page<CloudParam>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudParam> queryWrapper = new QueryWrapper();
        if (pageHelper.getData() != null) {
            CloudParam sysParam = pageHelper.getData();
            if (StringUtil.isNotEmpty(sysParam.getParamName())) {
                queryWrapper.lambda().like(CloudParam::getParamName, sysParam.getParamName());
            }
            if (StringUtil.isNotEmpty(sysParam.getParamKey())) {
                queryWrapper.lambda().like(CloudParam::getParamKey, sysParam.getParamKey());
            }
            if (StringUtil.isNotEmpty(sysParam.getSysFlag())) {
                queryWrapper.lambda().like(CloudParam::getSysFlag, sysParam.getSysFlag());
            }
        }
        return (Page<CloudParam>) this.page(page, queryWrapper);
    }

    @Override
    public CloudParam saveSysParam(CloudParam sysParam) {
        if (StringUtil.isEmpty(sysParam.getSysFlag())) {
            sysParam.setSysFlag(FlagEnum.NO.getCode());
        }
        validateSysParam(sysParam);
        //验证数据是否符合规则
        ValidateUtil.validateObject(sysParam);
        if (this.save(sysParam)) {
            return sysParam;
        } else {
            throw new FrameException(ResultCodeEnum.ERROR_SAVE_FAIL, "新增系统参数信息失败！");
        }
    }

    /**
     * 添加或更新校验键值是否存在
     *
     * @param sysParam
     */
    private void validateSysParam(CloudParam sysParam) {
        QueryWrapper<CloudParam> queryWrapper = new QueryWrapper<CloudParam>();
        if (Objects.nonNull(sysParam.getId())) {
            queryWrapper.lambda().ne(CloudParam::getId, sysParam.getId());
        }
        queryWrapper.lambda().eq(CloudParam::getParamKey, sysParam.getParamKey());
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("字典类型（" + sysParam.getParamKey() + "）已存在！");
        }
    }

    @Override
    public boolean updateSysParam(CloudParam sysParam) {
        CloudParam oleParam = this.getById(sysParam.getId());
        if (oleParam == null) {
            throw new ValidateException("参数已不存在，不能进行编辑！");
        }
        if (oleParam.getSysFlag().equals(FlagEnum.YES.getCode())) {
            if (false) {
                // if (!ShiroUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                throw new ValidateException("系统默认参数不能进行修改！");
            }
        }
        validateSysParam(sysParam);
        //验证数据是否符合规则
        ValidateUtil.validateObject(sysParam);

        return this.updateById(sysParam);
    }

    @Override
    public boolean deleteSysParam(Long paramId) {
        CloudParam sysParam = this.getById(paramId);
        if (sysParam == null) {
            return true;
        } else {
            if (sysParam.getSysFlag().equals(FlagEnum.YES.getCode())) {
                if (false) {
                    // if (!ShiroUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                    throw new ValidateException("系统默认参数不能进行删除！");
                }
            }
        }
        return this.removeById(paramId);
    }
}
