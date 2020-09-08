package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ICloudMenuService;
import com.jeeadmin.api.ICloudRoleMenuService;
import com.jeeadmin.entity.CloudMenu;
import com.jeeadmin.entity.CloudRoleMenu;
import com.jeeadmin.mapper.CloudRoleMenuMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Seven Lee
 * @description
 *      角色菜单关系表 服务实现类
 * @date 2020/9/9
**/
@Service
public class CloudRoleMenuServiceImpl extends BaseServiceImpl<CloudRoleMenuMapper, CloudRoleMenu> implements ICloudRoleMenuService {
    @Autowired
    private ICloudMenuService sysMenuService;

    @Override
    public boolean saveRoleMenu(List<CloudRoleMenu> sysRoleMenuList) {
        return this.saveBatch(sysRoleMenuList);
    }

    @Override
    public boolean deleteRoleMenu(Long roleId) {
        if (Objects.nonNull(roleId)) {
            QueryWrapper<CloudRoleMenu> whereWrapper = new QueryWrapper<CloudRoleMenu>();
            whereWrapper.lambda().eq(CloudRoleMenu::getRoleId, roleId);
            return this.remove(whereWrapper);
        } else {
            return true;
        }
    }

    @Override
    public List<CloudRoleMenu> detailMenuList(Long roleId) {
        QueryWrapper<CloudRoleMenu> queryWrapper = new QueryWrapper<CloudRoleMenu>();
        queryWrapper.lambda().eq(CloudRoleMenu::getRoleId, roleId);
        List<CloudRoleMenu> sysRoleMenusList = this.list(queryWrapper);
        List<CloudRoleMenu> menuList = new ArrayList<CloudRoleMenu>();
        for (CloudRoleMenu sysRoleMenu : sysRoleMenusList) {
            QueryWrapper<CloudMenu> queryMenuWrapper = new QueryWrapper<CloudMenu>();
            queryMenuWrapper.lambda().eq(CloudMenu::getParentId, sysRoleMenu.getMenuId());
            if (sysMenuService.count(queryMenuWrapper) < 1) {
                menuList.add(sysRoleMenu);
            }
        }
        return menuList;
    }
}
