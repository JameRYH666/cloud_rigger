package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudUserOrgService;
import com.jeeadmin.api.ICloudUserRoleService;
import com.jeeadmin.api.ISysOrgAdminService;
import com.jeeadmin.api.ICloudPartyMemberService;
import com.jeeadmin.entity.CloudUser;
import com.jeeadmin.entity.SysOrgAdminOrg;
import com.jeeadmin.entity.SysOrgAdminRole;
import com.jeeadmin.entity.SysUser;
import com.jeeadmin.vo.orgadmin.AssignOrgRoleVo;
import com.jeeadmin.vo.orgadmin.QueryOrgRoleVo;
import com.jeeadmin.vo.user.QueryUserVo;
import com.jeerigger.frame.enums.FlagEnum;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SysOrgAdminServiceImpl implements ISysOrgAdminService {
    @Autowired
    private ICloudUserOrgService sysOrgAdminOrgService;
    @Autowired
    private ICloudUserRoleService sysOrgAdminRoleService;
    @Autowired
    private ICloudPartyMemberService sysUserService;

    @Override
    public Page<CloudUser> selectOrgAdminPage(PageHelper<QueryUserVo> pageHelper) {
        Page<CloudUser> page = new Page<CloudUser>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper<CloudUser>();

        if (pageHelper.getData() != null) {
            QueryUserVo queryUserVo = pageHelper.getData();
            queryWrapper.lambda().eq(CloudUser::getMgrFlag, FlagEnum.YES.getCode());
            if (StringUtil.isNotEmpty(queryUserVo.getLoginName())) {
                queryWrapper.lambda().like(CloudUser::getLoginName, queryUserVo.getLoginName());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserName())) {
                queryWrapper.lambda().like(CloudUser::getLoginName, queryUserVo.getUserName());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserEmail())) {
                queryWrapper.lambda().like(CloudUser::getUserEmail, queryUserVo.getUserEmail());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserPhone())) {
                queryWrapper.lambda().like(CloudUser::getUserPhone, queryUserVo.getUserPhone());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserMobile())) {
                queryWrapper.lambda().like(SysUser::getUserMobile, queryUserVo.getUserMobile());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserStatus())) {
                queryWrapper.lambda().eq(SysUser::getUserStatus, queryUserVo.getUserStatus());
            }
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
    public Page<CloudUser> selectUserPage(PageHelper<QueryUserVo> pageHelper) {
        Page<CloudUser> page = new Page<CloudUser>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper();
        if (pageHelper.getData() != null) {
            QueryUserVo queryUserVo = pageHelper.getData();
            if (StringUtil.isNotEmpty(queryUserVo.getLoginName())) {
                queryWrapper.lambda().like(CloudUser::getLoginName, queryUserVo.getLoginName());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserName())) {
                queryWrapper.lambda().like(CloudUser::getUserName, queryUserVo.getUserName());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserEmail())) {
                queryWrapper.lambda().like(CloudUser::getUserEmail, queryUserVo.getUserEmail());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserPhone())) {
                queryWrapper.lambda().like(CloudUser::getUserPhone, queryUserVo.getUserPhone());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserMobile())) {
                queryWrapper.lambda().like(SysUser::getUserMobile, queryUserVo.getUserMobile());
            }
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
    public boolean saveOrgAdmin(Long userId) {
        //验证用户是否存在
        validateUser(userId);
        CloudUser sysUser = new CloudUser();
        sysUser.setId(userId);
        sysUser.setMgrFlag(FlagEnum.YES.getCode());
        return sysUserService.updateById(sysUser);
    }

    /**
     * 验证用户是否存在
     *
     * @param userId
     */
    private void validateUser(Long userId) {
        if (Objects.isNull(userId)) {
            throw new ValidateException("用户UUID不能为空！");
        }
        if (sysUserService.getById(userId) == null) {
            throw new ValidateException("该用户不存在！");
        }
    }

    @Override
    public boolean cancelOrgAdmin(Long userId) {
        //验证用户是否存在
        validateUser(userId);
        CloudUser sysUser = new CloudUser();
        sysUser.setId(userId);
        sysUser.setMgrFlag(FlagEnum.NO.getCode());
        //机构管理员需删除机构管理员组织机构关系表
        sysOrgAdminOrgService.deleteOrgAdminOrg(userId);
        //机构管理员需删除机构管理员角色关系表
        sysOrgAdminRoleService.deleteOrgAdminRole(userId);
        return sysUserService.updateById(sysUser);
    }

    @Override
    public QueryOrgRoleVo detailOrgRole(Long userId) {
        QueryOrgRoleVo orgAdminOrgRoleVo = new QueryOrgRoleVo();
        orgAdminOrgRoleVo.setUserId(userId);
        orgAdminOrgRoleVo.setOrgAdminOrgList(sysOrgAdminOrgService.detailOrgList(userId));
        orgAdminOrgRoleVo.setOrgAdminRoleList(sysOrgAdminRoleService.detailRoleList(userId));
        return orgAdminOrgRoleVo;
    }

    @Override
    public boolean assignOrgRole(AssignOrgRoleVo assignOrgRoleVo) {
        SysUser sysUser = sysUserService.getById(assignOrgRoleVo.getUserId());
        if (sysUser == null) {
            throw new ValidateException("用户不存在！");
        }
        if (!FlagEnum.YES.getCode().equals(sysUser.getMgrFlag())) {
            throw new ValidateException("该用户不是组织机构管理员不能进行分配！");
        }

        sysOrgAdminOrgService.deleteOrgAdminOrg(assignOrgRoleVo.getUserId());
        if (assignOrgRoleVo.getOrgIdList() != null && assignOrgRoleVo.getOrgIdList().size() > 0) {
            List<SysOrgAdminOrg> sysOrgAdminOrgList = new ArrayList<>();
            for (Long orgId : assignOrgRoleVo.getOrgIdList()) {
                SysOrgAdminOrg sysOrgAdminOrg = new SysOrgAdminOrg();
                sysOrgAdminOrg.setUserId(assignOrgRoleVo.getUserId());
                sysOrgAdminOrg.setOrgId(orgId);
                sysOrgAdminOrgList.add(sysOrgAdminOrg);
            }
            sysOrgAdminOrgService.saveOrgAdminOrg(sysOrgAdminOrgList);
        }
        sysOrgAdminRoleService.deleteOrgAdminRole(assignOrgRoleVo.getUserId());
        if (assignOrgRoleVo.getRoleIdList() != null && assignOrgRoleVo.getRoleIdList().size() > 0) {
            List<SysOrgAdminRole> sysOrgAdminRoleList = new ArrayList<>();
            for (Long roleId : assignOrgRoleVo.getRoleIdList()) {
                SysOrgAdminRole sysOrgAdminRole = new SysOrgAdminRole();
                sysOrgAdminRole.setUserId(assignOrgRoleVo.getUserId());
                sysOrgAdminRole.setRoleId(roleId);
                sysOrgAdminRoleList.add(sysOrgAdminRole);
            }
            sysOrgAdminRoleService.saveOrgAdminRole(sysOrgAdminRoleList);
        }
        return true;
    }
}
