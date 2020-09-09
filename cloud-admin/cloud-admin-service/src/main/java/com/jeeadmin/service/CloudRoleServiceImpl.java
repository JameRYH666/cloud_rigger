package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.*;
import com.jeeadmin.entity.*;
import com.jeeadmin.mapper.CloudRoleMapper;
import com.jeeadmin.vo.role.AssignMenuVo;
import com.jeeadmin.vo.role.AssignUserVo;
import com.jeeadmin.vo.user.QueryUserVo;
import com.jeerigger.core.module.sys.SysConstant;
import com.jeerigger.core.module.sys.util.SysDictUtil;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.FlagEnum;
import com.jeerigger.frame.enums.StatusEnum;
import com.jeerigger.frame.enums.UserTypeEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@Service
public class CloudRoleServiceImpl extends BaseServiceImpl<CloudRoleMapper, CloudRole> implements ICloudRoleService {
    @Autowired
    private ICloudUserService sysUserService;
    @Autowired
    private ICloudUserRoleService sysUserRoleService;
    @Autowired
    private ICloudRoleMenuService sysRoleMenuService;
    @Autowired
    private ICloudUserRoleService sysOrgAdminRoleService;

    @Override
    public Page<CloudRole> selectPage(PageHelper<CloudRole> pageHelper) {
        Page<CloudRole> page = new Page<CloudRole>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudRole> queryWrapper = new QueryWrapper<CloudRole>();
        if (pageHelper.getData() != null) {
            CloudRole sysRole = pageHelper.getData();
            //角色名称
            if (StringUtil.isNotEmpty(sysRole.getRoleName())) {
                queryWrapper.lambda().like(CloudRole::getRoleName, sysRole.getRoleName());
            }
            //角色编码
            if (StringUtil.isNotEmpty(sysRole.getRoleCode())) {
                queryWrapper.lambda().like(CloudRole::getRoleCode, sysRole.getRoleCode());
            }
            //角色状态
            if (StringUtil.isNotEmpty(sysRole.getRoleStatus())) {
                queryWrapper.lambda().eq(CloudRole::getRoleStatus, sysRole.getRoleStatus());
            }
            //是否系统
            if (StringUtil.isNotEmpty(sysRole.getSysFlag())) {
                queryWrapper.lambda().eq(CloudRole::getSysFlag, sysRole.getSysFlag());
            }
            if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                queryWrapper.lambda().ne(CloudRole::getRoleCode, SysConstant.SYS_ADMIN_ROLE);
            }
        }
        queryWrapper.lambda().orderByAsc(CloudRole::getRoleSort);
        this.page(page, queryWrapper);
        for (CloudRole sysRole : page.getRecords()) {
            if (StringUtil.isNotEmpty(sysRole.getRoleType())) {
                String roleTypeName = SysDictUtil.getDictLable(SysConstant.SYS_ROLE_TYPE, sysRole.getRoleType());
                sysRole.setRoleTypeName(roleTypeName);
            }
        }
        return page;
    }


    @Override
    public boolean updateStatus(Long roleId, String roleStatus) {
        CloudRole oldRole = this.getById(roleId);
        if (oldRole == null) {
            throw new ValidateException("该角色已不存在不能修改状态！");
        }
        if (oldRole.getRoleCode().equals(SysConstant.SYS_ADMIN_ROLE)) {
            throw new ValidateException("系统管理员默认角色不能进行更新状态！");
        }
        if (oldRole.getSysFlag().equals(FlagEnum.YES.getCode())) {
            if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                throw new ValidateException("系统内置角色不能修改状态！");
            }
        }
        CloudRole sysRole = new CloudRole();
        sysRole.setId(roleId);
        sysRole.setRoleStatus(roleStatus);
        return this.updateById(sysRole);
    }

    @Override
    public CloudRole saveSysRole(CloudRole sysRole) {
        //数据验证
        ValidateUtil.validateObject(sysRole);
        //验证角色编码
        validatorRoleCode(sysRole);
        //保存数据
        boolean saveFlag = this.save(sysRole);
        //保存角色菜单数据
        if (saveFlag) {
            saveFlag = saveRoleMenu(sysRole.getId(), sysRole.getMenuIdList());
        }
        if (saveFlag) {
            return sysRole;
        } else {
            throw new FrameException(ResultCodeEnum.ERROR_SAVE_FAIL, "新增角色信息失败！");
        }
    }

    /**
     * 保存角色菜单
     *
     * @param roleId
     * @param menuIdList
     * @return
     */
    private boolean saveRoleMenu(Long roleId, List<Long> menuIdList) {
        if (menuIdList != null && menuIdList.size() > 0) {
            List<CloudRoleMenu> sysRoleMenuList = new ArrayList<CloudRoleMenu>();
            for (Long menuId : menuIdList) {
                CloudRoleMenu sysRoleMenu = new CloudRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenuList.add(sysRoleMenu);
            }
            return sysRoleMenuService.saveRoleMenu(sysRoleMenuList);
        }
        return true;
    }

    /**
     * 验证角色编码
     */
    private void validatorRoleCode(CloudRole sysRole) {
        QueryWrapper<CloudRole> queryWrapper = new QueryWrapper<CloudRole>();
        if (Objects.nonNull(sysRole.getId())) {
            queryWrapper.lambda().ne(CloudRole::getId, sysRole.getId());
        }
        queryWrapper.lambda().eq(CloudRole::getRoleCode, sysRole.getRoleCode());
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("该角色编码(" + sysRole.getRoleCode() + ")已存在！");
        }
    }

    @Override
    public boolean updateSysRole(CloudRole sysRole) {
        CloudRole oldRole = this.getById(sysRole.getId());
        if (oldRole == null) {
            throw new ValidateException("该角色(" + sysRole.getRoleCode() + ")已不存在，不能进行编辑！");
        }
        if (oldRole.getRoleCode().equals(SysConstant.SYS_ADMIN_ROLE)) {
            throw new ValidateException("系统管理员默认角色不能进行更新！");
        }
        if (oldRole.getSysFlag().equals(FlagEnum.YES.getCode())) {
            if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                throw new ValidateException("系统内置角色不能进行编辑！");
            }
        }
        //数据验证
        ValidateUtil.validateObject(sysRole);
        //验证角色编码
        validatorRoleCode(sysRole);
        return this.updateById(sysRole);
    }

    @Override
    public boolean deleteSysRole(Long roleId) {
        CloudRole sysRole = this.getById(roleId);
        if (sysRole == null) {
            return true;
        } else {
            if (sysRole.getRoleCode().equals(SysConstant.SYS_ADMIN_ROLE)) {
                throw new ValidateException("系统管理员默认角色不能进行删除！");
            }
            if (sysRole.getSysFlag().equals(FlagEnum.YES.getCode())) {
                if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                    throw new ValidateException("系统内置角色不能进行删除！");
                }
            }
        }


        //查询该角色已分配人员
        QueryWrapper<CloudUserRole> userRoleWrapper = new QueryWrapper<CloudUserRole>();
        userRoleWrapper.lambda().eq(CloudUserRole::getRoleId, roleId);
        if (sysUserRoleService.count(userRoleWrapper) > 0) {
            throw new ValidateException("该角色（" + sysRole.getRoleCode() + "）已分配人员使用，不能删除！");
        }

        //查询该角色是否已分配给组织机构管理员
        QueryWrapper<CloudUserRole> orgAdminRoleWrapper = new QueryWrapper<>();
        orgAdminRoleWrapper.lambda().eq(CloudUserRole::getRoleId, roleId);
        if (sysOrgAdminRoleService.count(orgAdminRoleWrapper) > 0) {
            throw new ValidateException("该角色（" + sysRole.getRoleCode() + "）已分配组织机构管理员，不能删除！");
        }

        //删除角色菜单关联表信息
        if (sysRoleMenuService.deleteRoleMenu(roleId)) {
            //删除角色表
            if (this.removeById(roleId)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public List<CloudRole> selectRoleList() {
        QueryWrapper<CloudRole> queryWrapper = new QueryWrapper<CloudRole>();
        queryWrapper.lambda().eq(CloudRole::getRoleStatus, StatusEnum.NORMAL.getCode());
        queryWrapper.lambda().ne(CloudRole::getRoleCode, SysConstant.SYS_ADMIN_ROLE);
        queryWrapper.lambda().orderByAsc(CloudRole::getRoleSort);
        return this.list(queryWrapper);
    }

    @Override
    public Page<CloudUser> selectUserPage(PageHelper<QueryUserVo> pageHelper) {
        Page<CloudUser> page = new Page<CloudUser>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper();
        if (pageHelper.getData() != null) {
            QueryUserVo queryUserVo = pageHelper.getData();
            if (StringUtil.isNotEmpty(queryUserVo.getLoginName())) {
                queryWrapper.lambda().like(CloudUser::getLoginName, queryUserVo.getLoginName());
            }
            /*if (StringUtil.isNotEmpty(queryUserVo.getUserName())) {
                queryWrapper.lambda().like(CloudPartyMember::getUserName, queryUserVo.getUserName());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserEmail())) {
                queryWrapper.lambda().like(CloudPartyMember::getEmail, queryUserVo.getUserEmail());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserPhone())) {
                queryWrapper.lambda().like(CloudPartyMember::getMemberPhoneNumber, queryUserVo.getUserPhone());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserMobile())) {
                queryWrapper.lambda().like(CloudPartyMember::getUserMobile, queryUserVo.getUserMobile());
            }*/
        }
        sysUserService.page(page, queryWrapper);
//        for (SysUser sysUser : page.getRecords()) {
//            if (StringUtil.isNotEmpty(sysUser.getOrgUuid())) {
//                sysUser.setOrgName(OrgUtil.getOrgName(sysUser.getOrgUuid()));
//            }
//        }
        return page;
    }

    @Override
    public boolean assignUser(AssignUserVo assignUserVo) {
        CloudRole sysRole = this.getById(assignUserVo.getRoleId());
        if (sysRole == null) {
            throw new ValidateException("当前操作角色已不存在！");
        } else {
            if (sysRole.getRoleCode().equals(SysConstant.SYS_ADMIN_ROLE)) {
                throw new ValidateException("当前角色为系统管理员默认角色不能授予任何用户！");
            }
        }
        if (assignUserVo.getUserIdList() != null && assignUserVo.getUserIdList().size() > 0) {
            List<CloudUserRole> sysUserRoleList = new ArrayList<CloudUserRole>();
            for (Long userId : assignUserVo.getUserIdList()) {
                CloudUserRole sysUserRole = new CloudUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(assignUserVo.getRoleId());
                QueryWrapper<CloudUserRole> queryWrapper = new QueryWrapper<>(sysUserRole);
                if (sysUserRoleService.count(queryWrapper) < 1) {
                    sysUserRoleList.add(sysUserRole);
                }
            }
            //保存用户角色
            return sysUserRoleService.saveOrgAdminRole(sysUserRoleList);
        }
        return true;
    }

    @Override
    public boolean assignMenu(AssignMenuVo assignMenuVo) {
        CloudRole sysRole = this.getById(assignMenuVo.getRoleId());
        if (sysRole == null) {
            throw new ValidateException("当前操作角色已不存在！");
        } else {
            if (sysRole.getRoleCode().equals(SysConstant.SYS_ADMIN_ROLE)) {
                if (!SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
                    throw new ValidateException("该角色只有超级管理员才能分配菜单！");
                }
            }
        }
        boolean assignMenuFlag = sysRoleMenuService.deleteRoleMenu(assignMenuVo.getRoleId());
        if (assignMenuFlag) {
            assignMenuFlag = saveRoleMenu(assignMenuVo.getRoleId(), assignMenuVo.getMenuIdList());
        }
        return assignMenuFlag;
    }
}
