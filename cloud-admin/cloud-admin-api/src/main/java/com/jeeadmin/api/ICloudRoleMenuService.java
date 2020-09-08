package com.jeeadmin.api;

import com.jeeadmin.entity.CloudRoleMenu;
import com.jeerigger.frame.base.service.BaseService;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      角色菜单关系表 服务类
 * @date 2020/9/8
**/
public interface ICloudRoleMenuService extends BaseService<CloudRoleMenu> {
    /**
     * 保存角色菜单
     *
     * @param sysRoleMenuList
     * @return
     */
    boolean saveRoleMenu(List<CloudRoleMenu> sysRoleMenuList);

    /**
     * 根据角色UUID删除角色菜单
     *
     * @param roleId
     * @return
     */
    boolean deleteRoleMenu(Long roleId);

    /**
     * 根据角色uuid获取已分配的菜单
     *
     * @param roleId
     * @return
     */
    List<CloudRoleMenu> detailMenuList(Long roleId);

}
