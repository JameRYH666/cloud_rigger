package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ISysMenuService;
import com.jeeadmin.api.ISysRoleMenuService;
import com.jeeadmin.entity.SysMenu;
import com.jeeadmin.entity.SysRoleMenu;
import com.jeeadmin.mapper.SysMenuMapper;
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
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@Service
public class SysMenuServiceImpl extends BaseTreeServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenu> selectChildMenu(QueryMenuVo queryMenuVo) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(queryMenuVo.getMenuId())) {
            queryMenuVo.setMenuId(0L);
        }
        wrapper.lambda().eq(SysMenu::getParentId, queryMenuVo.getMenuId());
        if (StringUtil.isNotEmpty(queryMenuVo.getSysCode())) {
            wrapper.lambda().eq(SysMenu::getSysCode, queryMenuVo.getSysCode());
        }
        wrapper.lambda().orderByAsc(SysMenu::getParentId, SysMenu::getMenuSort);
        return getListMenu(wrapper);
    }

    @Override
    public List<SysMenu> selectMenuList(QueryMenuVo queryMenuVo) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(queryMenuVo.getMenuName())) {
            wrapper.lambda().like(SysMenu::getMenuName, queryMenuVo.getMenuName());
        }
        if (StringUtil.isNotEmpty(queryMenuVo.getSysCode())) {
            wrapper.lambda().eq(SysMenu::getSysCode, queryMenuVo.getSysCode());
        }
        wrapper.lambda().orderByAsc(SysMenu::getParentId, SysMenu::getMenuSort);
        return getListMenu(wrapper);
    }

    private List<SysMenu> getListMenu(QueryWrapper<SysMenu> queryWrapper) {
        List<SysMenu> sysMenuList = this.list(queryWrapper);
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getMenuWeight() != null) {
                String menuWeightName = SysDictUtil.getDictLable(SysConstant.SYS_MENU_WEIGHT,
                        sysMenu.getMenuWeight().toString());
                sysMenu.setMenuWeightName(menuWeightName);
            }
        }
        return sysMenuList;
    }

    @Override
    public SysMenu saveMenu(SysMenu sysMenu) {
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
        List<SysMenu> menuList = new ArrayList<>();
        for (SaveMenuSortVo menuSortVo : menuSortVoList) {
            SysMenu sysMenu = new SysMenu();
            sysMenu.setId(menuSortVo.getMenuId());
            sysMenu.setMenuSort(menuSortVo.getMenuSort());
            menuList.add(sysMenu);
        }
        return this.updateBatchById(menuList);
    }

    @Override
    public boolean updateMenu(SysMenu sysMenu) {
        if (Objects.nonNull(sysMenu.getParentId())) {
            sysMenu.setParentId(0L);
        }
        SysMenu oldSysMenu = this.getById(sysMenu.getId());
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
                List<SysMenu> menuList = new ArrayList<>();
                List<Long> menuPkList = this.getChildrenPk(sysMenu.getId());
                menuPkList.addAll(this.getParentPk(sysMenu.getId()));
                for (Long id : menuPkList) {
                    SysMenu menu = new SysMenu();
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
    private void validateMenuName(SysMenu sysMenu) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(sysMenu.getId())) {
            queryWrapper.lambda().ne(SysMenu::getId, sysMenu.getId());
        }
        queryWrapper.lambda().eq(SysMenu::getParentId, sysMenu.getParentId());
        queryWrapper.lambda().eq(SysMenu::getSysCode, sysMenu.getSysCode());
        queryWrapper.lambda().eq(SysMenu::getMenuName, sysMenu.getMenuName());
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
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleMenu::getMenuId, menuId);
        if (sysRoleMenuService.count(wrapper) > 0) {
            throw new ValidateException("该菜单已与角色绑定，请解除绑定后再删除!");
        }
        return this.removeById(menuId);
    }

    @Override
    public List<SysMenu> getMenuList() {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        //获取超级管理员权重以外的菜单
        wrapper.lambda().ne(SysMenu::getMenuWeight, SysConstant.MENU_WEIGHT_SUPER);
        wrapper.lambda().ne(SysMenu::getSysCode, SysCodeEnum.JEE_ADMIN_SYSTEM);
        wrapper.lambda().orderByAsc(SysMenu::getSysCode, SysMenu::getParentId, SysMenu::getMenuSort);
        return this.list(wrapper);
    }

    @Override
    public List<SysMenu> findAdminUserSysMenu(Long userId, String userType) {
        if (UserTypeEnum.SUPER_ADMIN_USER.getCode().equals(userType)) {
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            //获取超级管理员权重以外的菜单
            wrapper.lambda().eq(SysMenu::getSysCode, SysCodeEnum.JEE_ADMIN_SYSTEM);
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
        if (UserTypeEnum.SUPER_ADMIN_USER.getCode().equals(userType)) {
            // 超级管理员
            return true;
        }
        return false; // ListTools.isNotEmpty(getAdminUserPermission(userId, permission));
    }
}
