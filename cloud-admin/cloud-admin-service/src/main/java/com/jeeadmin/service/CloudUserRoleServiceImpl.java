package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ICloudUserRoleService;
import com.jeeadmin.entity.CloudUserRole;
import com.jeeadmin.mapper.CloudUserRoleMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Seven Lee
 * @description
 *      机构管理员  角色分配表 服务实现类
 * @date 2020/9/9
**/
@Service
public class CloudUserRoleServiceImpl extends BaseServiceImpl<CloudUserRoleMapper, CloudUserRole> implements ICloudUserRoleService {

    @Override
    public boolean saveOrgAdminRole(List<CloudUserRole> sysOrgAdminRoleList) {
        return this.saveBatch(sysOrgAdminRoleList);
    }

    @Override
    public boolean deleteOrgAdminRole(Long userId) {
        if (Objects.nonNull(userId)) {
            QueryWrapper<CloudUserRole> wrapper = new QueryWrapper<CloudUserRole>();
            wrapper.lambda().eq(CloudUserRole::getUserId, userId);
            return this.remove(wrapper);
        } else {
            return true;
        }
    }

    @Override
    public List<CloudUserRole> detailRoleList(Long userId) {
        QueryWrapper<CloudUserRole> queryWrapper = new QueryWrapper<CloudUserRole>();
        queryWrapper.lambda().eq(CloudUserRole::getUserId, userId);
        return this.list(queryWrapper);
    }

}
