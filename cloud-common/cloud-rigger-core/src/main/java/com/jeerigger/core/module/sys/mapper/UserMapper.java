package com.jeerigger.core.module.sys.mapper;

import com.jeerigger.frame.mybatis.annotation.MapperSource;
import com.jeerigger.core.module.sys.entity.SysAdminUser;
import com.jeerigger.core.module.sys.entity.SysUser;
import com.jeerigger.core.module.sys.entity.UserMenu;
import com.jeerigger.core.module.sys.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@MapperSource
public interface UserMapper {
    /**
     * 获取系统管理员信息
     *
     * @param loginName
     * @return
     */
    @Select("select * from sys_admin_user where login_name=#{loginName}")
    SysAdminUser getSysAdminUser(@Param("loginName") String loginName);

    /**
     * 获取用户信息
     *
     * @param loginName
     * @return
     */
    @Select("select * from sys_user where login_name=#{loginName}")
    SysUser getSysUser(@Param("loginName") String loginName);

    /**
     * 获取系统管理员菜单
     *
     * @param role_code
     * @return
     */
    @Select("SELECT DISTINCT m.* FROM cloud_role sr,cloud_role_menu rm,cloud_menu m WHERE sr.id=rm.role_id AND rm.menu_id=m.id AND sr.role_code=#{role_code} ORDER BY m.parent_id,m.menu_sort ASC")
    List<UserMenu> getAdminUserMenu(@Param("role_code") String role_code);
    /**
     * 获取超级管理员菜单
     *
     * @return
     */
    @Select("select m.* from sys_menu m " +
            "where m.sys_code ='jeeadmin' " +
            "order by m.parent_uuid,m.menu_sort asc")
    List<UserMenu> getSuperAdminMenu();
    /**
     * 获取用户菜单
     *
     * @return
     */
    @Select("select distinct m.* from cloud_user u,cloud_user_role ur,cloud_role_menu rm,cloud_menu m where u.id=ur.user_id and ur.role_id=rm.role_id and rm.menu_id=m.id and u.id=${userid} order by m.id,m.menu_sort asc")
    List<UserMenu> getUserMenu(@Param("userId") Long userId);
    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    @Select("select distinct r.* from cloud_user u,cloud_user_role ur,cloud_role r where u.id=ur.user_id and ur.role_id=r.id and u.id= ${userid}")
    List<UserRole> getUserRole(@Param("userId") Long userId);
}
