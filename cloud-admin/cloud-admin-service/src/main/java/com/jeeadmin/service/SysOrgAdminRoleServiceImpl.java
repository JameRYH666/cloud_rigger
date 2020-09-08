package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ISysOrgAdminRoleService;
import com.jeeadmin.entity.SysOrgAdminRole;
import com.jeeadmin.mapper.SysOrgAdminRoleMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 机构管理员  角色分配表 服务实现类
 * </p>
 *
 * @author wangcy
 * @since 2018-11-15
 */
@Service
public class SysOrgAdminRoleServiceImpl extends BaseServiceImpl<SysOrgAdminRoleMapper, SysOrgAdminRole> implements ISysOrgAdminRoleService {

    @Override
    public boolean saveOrgAdminRole(List<SysOrgAdminRole> sysOrgAdminRoleList) {
        return this.saveBatch(sysOrgAdminRoleList);
    }

    @Override
    public boolean deleteOrgAdminRole(Long userId) {
        if (Objects.nonNull(userId)) {
            QueryWrapper<SysOrgAdminRole> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SysOrgAdminRole::getUserId, userId);
            return this.remove(wrapper);
        } else {
            return true;
        }
    }

    @Override
    public List<SysOrgAdminRole> detailRoleList(Long userId) {
        QueryWrapper<SysOrgAdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysOrgAdminRole::getUserId, userId);
        return this.list(queryWrapper);
    }

}
