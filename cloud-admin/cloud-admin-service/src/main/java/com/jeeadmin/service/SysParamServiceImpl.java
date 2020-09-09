package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ISysParamService;
import com.jeeadmin.entity.SysParam;
import com.jeeadmin.mapper.SysParamMapper;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.FlagEnum;
import com.jeerigger.frame.enums.UserTypeEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 系统参数配置表 服务实现类
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@Service
public class SysParamServiceImpl extends BaseServiceImpl<SysParamMapper, SysParam> implements ISysParamService {

    @Override
    public Page<SysParam> selectPage(PageHelper<SysParam> pageHelper) {
        Page<SysParam> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<SysParam> queryWrapper = new QueryWrapper();
        if (pageHelper.getData() != null) {
            SysParam sysParam = pageHelper.getData();
            if (StringUtil.isNotEmpty(sysParam.getParamName())) {
                queryWrapper.lambda().like(SysParam::getParamName, sysParam.getParamName());
            }
            if (StringUtil.isNotEmpty(sysParam.getParamKey())) {
                queryWrapper.lambda().like(SysParam::getParamKey, sysParam.getParamKey());
            }
            if (StringUtil.isNotEmpty(sysParam.getSysFlag())) {
                queryWrapper.lambda().like(SysParam::getSysFlag, sysParam.getSysFlag());
            }
        }
        return (Page<SysParam>) this.page(page, queryWrapper);
    }

    @Override
    public SysParam saveSysParam(SysParam sysParam) {
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
    private void validateSysParam(SysParam sysParam) {
        QueryWrapper<SysParam> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(sysParam.getId())) {
            queryWrapper.lambda().ne(SysParam::getId, sysParam.getId());
        }
        queryWrapper.lambda().eq(SysParam::getParamKey, sysParam.getParamKey());
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("字典类型（" + sysParam.getParamKey() + "）已存在！");
        }
    }

    @Override
    public boolean updateSysParam(SysParam sysParam) {
        SysParam oleParam = this.getById(sysParam.getId());
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
        SysParam sysParam = this.getById(paramId);
        if (sysParam == null) {
            return true;
        } else {
            if (sysParam.getSysFlag().equals(FlagEnum.YES.getCode())) {
                if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                    throw new ValidateException("系统默认参数不能进行删除！");
                }
            }
        }
        return this.removeById(paramId);
    }
}
