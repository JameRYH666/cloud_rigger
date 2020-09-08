package com.jeerigger.core.module.sys.service;

import com.jeerigger.core.module.sys.entity.SysAdminUser;
import com.jeerigger.core.module.sys.entity.SysUser;
import com.jeerigger.core.module.sys.entity.UserMenu;
import com.jeerigger.core.module.sys.entity.UserRole;

import java.util.List;

public interface IUserService {
    /**
     * 获取系统管理员信息
     *
     * @param loginName
     * @return
     */
    SysAdminUser getSysAdminUser(String loginName);

    /**
     * 获取用户信息
     *
     * @param loginName
     * @return
     */
    SysUser getSysUser(String loginName);


    /**
     * 获取系统管理员菜单
     *
     * @param role_code
     * @return
     */
    List<UserMenu> getAdminUserMenu(String role_code);

    /**
     * 获取超级管理员菜单
     *
     * @return
     */
    List<UserMenu> getSuperAdminMenu();


    /**
     * 获取用户菜单
     *
     * @return
     */
    List<UserMenu> getUserMenu(String id);


    /**
     * 获取用户角色
     *
     * @param id
     * @return
     */
    List<UserRole> getUserRole(String id);
}
