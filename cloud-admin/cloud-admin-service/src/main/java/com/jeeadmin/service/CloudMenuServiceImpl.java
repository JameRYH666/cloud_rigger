package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ICloudMenuService;
import com.jeeadmin.api.ICloudRoleMenuService;
import com.jeeadmin.entity.CloudMenu;
import com.jeeadmin.entity.CloudRoleMenu;
import com.jeeadmin.mapper.CloudMenuMapper;
import com.jeeadmin.vo.menu.QueryMenuVo;
import com.jeeadmin.vo.menu.SaveMenuSortVo;
import com.jeerigger.core.module.sys.SysConstant;
import com.jeerigger.core.module.sys.util.SysDictUtil;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.service.impl.BaseTreeServiceImpl;
import com.jeerigger.frame.enums.SysCodeEnum;
import com.jeerigger.frame.enums.UserTypeEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Seven Lee
 * @description
 *      系统菜单表 服务实现类
 * @date 2020/9/9
**/
@Service
public class CloudMenuServiceImpl extends BaseTreeServiceImpl<CloudMenuMapper, CloudMenu> implements ICloudMenuService {

    @Autowired
    private ICloudRoleMenuService sysRoleMenuService;

    @Override
    public List<CloudMenu> selectChildMenu(QueryMenuVo queryMenuVo) {
        QueryWrapper<CloudMenu> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(queryMenuVo.getMenuId())) {
            queryMenuVo.setMenuId(queryMenuVo.getMenuId());
        }
        wrapper.lambda().eq(CloudMenu::getParentId, queryMenuVo.getMenuId());
        if (StringUtil.isNotEmpty(queryMenuVo.getSysCode())) {
            wrapper.lambda().eq(CloudMenu::getSysCode, queryMenuVo.getSysCode());
        }
        wrapper.lambda().orderByAsc(CloudMenu::getParentId, CloudMenu::getMenuSort);
        return getListMenu(wrapper);
    }

    @Override
    public List<CloudMenu> selectMenuList(QueryMenuVo queryMenuVo) {
        QueryWrapper<CloudMenu> wrapper = new QueryWrapper<CloudMenu>();
        if (StringUtil.isNotEmpty(queryMenuVo.getMenuName())) {
            wrapper.lambda().like(CloudMenu::getMenuName, queryMenuVo.getMenuName());
        }
        if (StringUtil.isNotEmpty(queryMenuVo.getSysCode())) {
            wrapper.lambda().eq(CloudMenu::getSysCode, queryMenuVo.getSysCode());
        }
        wrapper.lambda().orderByAsc(CloudMenu::getParentId, CloudMenu::getMenuSort);
        return getListMenu(wrapper);
    }

    private List<CloudMenu> getListMenu(QueryWrapper<CloudMenu> queryWrapper) {
        List<CloudMenu> sysMenuList = this.list(queryWrapper);
        for (CloudMenu sysMenu : sysMenuList) {
            if (sysMenu.getMenuWeight() != null) {
                String menuWeightName = SysDictUtil.getDictLable(SysConstant.SYS_MENU_WEIGHT,
                        sysMenu.getMenuWeight().toString());
                sysMenu.setMenuWeightName(menuWeightName);
            }
        }
        return sysMenuList;
    }

    @Override
    public CloudMenu saveMenu(CloudMenu sysMenu) {
        if (Objects.isNull(sysMenu.getParentId())) {
            sysMenu.setParentId(0L);
        }
        if (StringUtil.isEmpty(sysMenu.getSysCode())) {
            sysMenu.setSysCode(SysCodeEnum.JEE_ADMIN_SYSTEM.getCode());
        }
        if (sysMenu.getMenuType().equals("1")) {
            sysMenu.setPermission("");
        }
        ValidateUtil.validateObject(sysMenu);
        //如果父节点不为空则判断父节点是否存在
        validateParentId(sysMenu.getParentId());
        //验证同级目录下是否已存在菜单名称
        validateMenuName(sysMenu);
        if (this.save(sysMenu)) {
            return sysMenu;
        } else {
            throw new FrameException(ResultCodeEnum.ERROR_SAVE_FAIL, "新增菜单失败!");
        }
    }

    @Override
    public boolean saveMenuSort(List<SaveMenuSortVo> menuSortVoList) {
        List<CloudMenu> menuList = new ArrayList<>();
        for (SaveMenuSortVo menuSortVo : menuSortVoList) {
            CloudMenu sysMenu = new CloudMenu();
            sysMenu.setId(menuSortVo.getMenuId());
            sysMenu.setMenuSort(menuSortVo.getMenuSort());
            menuList.add(sysMenu);
        }
        return this.updateBatchById(menuList);
    }

    @Override
    public boolean updateMenu(CloudMenu sysMenu) {
        if (Objects.nonNull(sysMenu.getParentId())) {
            sysMenu.setParentId(0L);
        }
        CloudMenu oldSysMenu = this.getById(sysMenu.getId());
        if (oldSysMenu == null) {
            throw new ValidateException("该菜单不存在,不能进行更新！");
        }
        if (StringUtil.isEmpty(sysMenu.getSysCode())) {
            sysMenu.setSysCode(oldSysMenu.getSysCode());
        }
        ValidateUtil.validateObject(sysMenu);
        //如果父节点不为空则判断父节点是否存在
        validateParentId(sysMenu.getParentId());
        //验证同级目录下是否已存在菜单名称
        validateMenuName(sysMenu);
        if (this.updateById(sysMenu)) {
            if (!oldSysMenu.getSysCode().equals(sysMenu.getSysCode())) {
                List<CloudMenu> menuList = new ArrayList<CloudMenu>();
                List<Long> menuPkList = this.getChildrenPk(sysMenu.getId());
                menuPkList.addAll(this.getParentPk(sysMenu.getId()));
                for (Long id : menuPkList) {
                    CloudMenu menu = new CloudMenu();
                    menu.setSysCode(sysMenu.getSysCode());
                    menu.setId(id);
                    menuList.add(menu);
                }
                this.updateBatchById(menuList);
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * 验证上级菜单是否存在
     *
     * @param menuId
     */
    private void validateParentId(Long menuId) {
        if (Objects.nonNull(menuId) && !Objects.equals(0L, menuId)) {
            if (this.getById(menuId) == null) {
                throw new ValidateException("选择的上级菜单不存在！");
            }
        }
    }

    /**
     * 验证同一级下名称是否存在
     */
    private void validateMenuName(CloudMenu sysMenu) {
        QueryWrapper<CloudMenu> queryWrapper = new QueryWrapper<CloudMenu>();
        if (Objects.nonNull(sysMenu.getId())) {
            queryWrapper.lambda().ne(CloudMenu::getId, sysMenu.getId());
        }
        queryWrapper.lambda().eq(CloudMenu::getParentId, sysMenu.getParentId());
        queryWrapper.lambda().eq(CloudMenu::getSysCode, sysMenu.getSysCode());
        queryWrapper.lambda().eq(CloudMenu::getMenuName, sysMenu.getMenuName());
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("菜单名称已存在，请核实！");
        }
    }

    @Override
    public boolean deleteMenu(Long menuId) {
        // 查询当前菜单的所有下级菜单
        if (Objects.isNull(menuId)) {
            throw new ValidateException("菜单UUID不能为空！");
        }
        List<Long> list = this.getChildrenPk(menuId);
        if (list != null && list.size() > 0) {
            throw new ValidateException("请先删除下级菜单！");
        }
        QueryWrapper<CloudRoleMenu> wrapper = new QueryWrapper<CloudRoleMenu>();
        wrapper.lambda().eq(CloudRoleMenu::getMenuId, menuId);
        if (sysRoleMenuService.count(wrapper) > 0) {
            throw new ValidateException("该菜单已与角色绑定，请解除绑定后再删除!");
        }
        return this.removeById(menuId);
    }

    @Override
    public List<CloudMenu> getMenuList() {
        QueryWrapper<CloudMenu> wrapper = new QueryWrapper<CloudMenu>();
        //获取超级管理员权重以外的菜单
        wrapper.lambda().ne(CloudMenu::getMenuWeight, SysConstant.MENU_WEIGHT_SUPER);
        wrapper.lambda().ne(CloudMenu::getSysCode, SysCodeEnum.JEE_ADMIN_SYSTEM);
        wrapper.lambda().orderByAsc(CloudMenu::getSysCode, CloudMenu::getParentId, CloudMenu::getMenuSort);
        return this.list(wrapper);
    }

    @Override
    public List<CloudMenu> findAdminUserSysMenu(Long userId, String userType) {
        if (UserTypeEnum.SYSTEM_ADMIN_USER.getCode().equals(userType)) {
            // 如果是超级管理员
            QueryWrapper<CloudMenu> wrapper = new QueryWrapper<CloudMenu>();
            wrapper.orderByAsc("parent_id", "menu_sort");
            // 超级管理员
            return this.list(wrapper);
        } else {
            // 系统管理员
            return null;// this.getAdminUserSysMenu(userId);
        }
    }

    @Override
    public boolean isAdminUserWithHasPermission(Long userId, String userType, String permission) {
        if (UserTypeEnum.SYSTEM_ADMIN_USER.getCode().equals(userType)) {
            // 超级管理员，拥有所有权限
            return true;
        }
        return false; // ListTools.isNotEmpty(getAdminUserPermission(userId, permission));
    }
}
