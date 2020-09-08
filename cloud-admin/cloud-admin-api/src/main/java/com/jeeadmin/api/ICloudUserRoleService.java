package com.jeeadmin.api;

import com.jeeadmin.entity.CloudUserRole;
import com.jeerigger.frame.base.service.BaseService;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      机构管理员  角色分配表 服务类
 * @date 2020/9/8
**/
public interface ICloudUserRoleService extends BaseService<CloudUserRole> {

    /**
     * 保存机构管理员 可分配的角色
     *
     * @param sysOrgAdminRoleList
     * @return
     */
    boolean saveOrgAdminRole(List<CloudUserRole> sysOrgAdminRoleList);

    /**
     * 删除机构管理员已分配 可分配的角色
     *
     * @param userId
     * @return
     */
    boolean deleteOrgAdminRole(Long userId);

    /**
     * 查看组织机构管理员已分配的可分配的角色
     *
     * @param userId
     * @return
     */
    List<CloudUserRole> detailRoleList(Long userId);
}
