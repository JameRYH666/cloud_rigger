package com.jeeadmin.api;

import com.jeeadmin.entity.CloudMenu;
import com.jeeadmin.vo.menu.QueryMenuVo;
import com.jeeadmin.vo.menu.SaveMenuSortVo;
import com.jeerigger.frame.base.service.BaseTreeService;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      系统菜单表 服务类
 * @date 2020/9/8
**/
public interface ICloudMenuService extends BaseTreeService<CloudMenu> {

    /**
     * 根据菜单ID获取下级菜单
     *
     * @param queryMenuVo
     * @return
     */
    List<CloudMenu> selectChildMenu(QueryMenuVo queryMenuVo);

    /**
     * 查询所有菜单
     *
     * @param queryMenuVo
     * @return
     */
    List<CloudMenu> selectMenuList(QueryMenuVo queryMenuVo);

    /**
     * 新增菜单
     *
     * @param sysMenu
     * @return
     */
    CloudMenu saveMenu(CloudMenu sysMenu);

    /**
     * 批量保存菜单排序
     *
     * @param menuSortVoList
     * @return
     */
    boolean saveMenuSort(List<SaveMenuSortVo> menuSortVoList);

    /**
     * 更新菜单
     *
     * @param sysMenu
     * @return
     */
    boolean updateMenu(CloudMenu sysMenu);

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    boolean deleteMenu(Long menuId);


    /**
     * 获取角色可分配菜单
     *
     * @return
     */
    List<CloudMenu> getMenuList();

    /**
     * 获取系统管理员或超级管理员菜单
     *
     * @param userId
     * @return
     */
    List<CloudMenu> findAdminUserSysMenu(Long userId, String userType);

    /**
     * 判断系统管理员是否有指定权限
     */
    boolean isAdminUserWithHasPermission(Long userId, String userType, String permission);
}
