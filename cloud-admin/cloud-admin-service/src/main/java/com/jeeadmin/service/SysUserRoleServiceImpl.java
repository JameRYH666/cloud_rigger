package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ISysUserRoleService;
import com.jeeadmin.entity.SysUserRole;
import com.jeeadmin.mapper.SysUserRoleMapper;
import com.jeeadmin.vo.role.CancelUserVo;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 人员角色关系表 服务实现类
 * </p>
 *
 * @author wangcy
 * @since 2018-11-15
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public boolean saveUserRole(List<SysUserRole> sysUserRoleList) {
        return this.saveBatch(sysUserRoleList);
    }

    @Override
    public boolean deleteUserRole(Long userId) {
        QueryWrapper<SysUserRole> whereWrapper = new QueryWrapper<>();
        if (Objects.nonNull(userId)) {
            whereWrapper.lambda().eq(SysUserRole::getUserId, userId);
            return this.remove(whereWrapper);
        } else {
            return true;
        }
    }

    @Override
    public List<SysUserRole> detailRoleList(Long userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRole::getUserId, userId);
        return this.list(queryWrapper);
    }

    @Override
    public boolean cancelRoleUser(CancelUserVo cancelUserVo) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUserRole::getRoleId, cancelUserVo.getRoleId());
        queryWrapper.lambda().in(SysUserRole::getUserId, cancelUserVo.getUserIdList());
        return this.remove(queryWrapper);
    }
}
