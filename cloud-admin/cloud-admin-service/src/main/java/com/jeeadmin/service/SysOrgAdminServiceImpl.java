package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.*;
import com.jeeadmin.entity.CloudUser;
import com.jeeadmin.entity.CloudUserOrg;
import com.jeeadmin.entity.CloudUserRole;
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
    private ICloudUserOrgService cloudUserOrgService;
    @Autowired
    private ICloudUserRoleService cloudUserRoleService;
    @Autowired
    private ICloudPartyMemberService cloudPartyMemberService;

    @Autowired
    private ICloudUserService cloudUserService;

    @Override
    public Page<CloudUser> selectOrgAdminPage(PageHelper<QueryUserVo> pageHelper) {
        Page<CloudUser> page = new Page<CloudUser>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper<CloudUser>();
        if (pageHelper.getData() != null) {
            QueryUserVo queryUserVo = pageHelper.getData();
            queryWrapper.lambda().eq(CloudUser::getUserStatus, FlagEnum.YES.getCode());
            if (StringUtil.isNotEmpty(queryUserVo.getLoginName())) {
                queryWrapper.lambda().like(CloudUser::getLoginName, queryUserVo.getLoginName());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getLoginName())) {
                queryWrapper.lambda().like(CloudUser::getLoginName, queryUserVo.getLoginName());
            }
        }
        cloudUserService.page(page, queryWrapper);
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
            if (StringUtil.isNotEmpty(queryUserVo.getLoginName())) {
                queryWrapper.lambda().like(CloudUser::getLoginName, queryUserVo.getLoginName());
            }
        }
        cloudUserService.page(page, queryWrapper);
        return page;
    }

    @Override
    public boolean saveOrgAdmin(Long userId) {
        //验证用户是否存在
        validateUser(userId);
        CloudUser cloudUser = new CloudUser();
        cloudUser.setId(userId);
        cloudUser.setUserStatus(FlagEnum.YES.getCode());
        return cloudUserService.updateById(cloudUser);
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
        if (cloudPartyMemberService.getById(userId) == null) {
            throw new ValidateException("该用户不存在！");
        }
    }

    @Override
    public boolean cancelOrgAdmin(Long userId) {
        //验证用户是否存在
        validateUser(userId);
        CloudUser cloudUser = new CloudUser();
        cloudUser.setId(userId);
        cloudUser.setUserStatus(FlagEnum.NO.getCode());
        //机构管理员需删除机构管理员组织机构关系表
        cloudUserOrgService.deleteOrgAdminOrg(userId);
        //机构管理员需删除机构管理员角色关系表
        cloudUserRoleService.deleteOrgAdminRole(userId);
        return cloudUserService.updateById(cloudUser);
    }

    @Override
    public QueryOrgRoleVo detailOrgRole(Long userId) {
        QueryOrgRoleVo orgAdminOrgRoleVo = new QueryOrgRoleVo();
        orgAdminOrgRoleVo.setUserId(userId);
        orgAdminOrgRoleVo.setOrgAdminOrgList(cloudUserOrgService.detailOrgList(userId));
        orgAdminOrgRoleVo.setOrgAdminRoleList(cloudUserRoleService.detailRoleList(userId));
        return orgAdminOrgRoleVo;
    }

    @Override
    public boolean assignOrgRole(AssignOrgRoleVo assignOrgRoleVo) {
        CloudUser cloudUser = cloudUserService.getById(assignOrgRoleVo.getUserId());
        if (cloudUser == null) {
            throw new ValidateException("用户不存在！");
        }
        if (!FlagEnum.YES.getCode().equals(cloudUser.getUserStatus())) {
            throw new ValidateException("该用户不是组织机构管理员不能进行分配！");
        }
        cloudUserOrgService.deleteOrgAdminOrg(assignOrgRoleVo.getUserId());
        if (assignOrgRoleVo.getOrgIdList() != null && assignOrgRoleVo.getOrgIdList().size() > 0) {
            List<CloudUserOrg> sysOrgAdminOrgList = new ArrayList<>();
            for (Long orgId : assignOrgRoleVo.getOrgIdList()) {
                CloudUserOrg cloudUserOrg = new CloudUserOrg();
                cloudUserOrg.setUserId(assignOrgRoleVo.getUserId());
                cloudUserOrg.setOrgId(orgId);
                sysOrgAdminOrgList.add(cloudUserOrg);
            }
            cloudUserOrgService.saveOrgAdminOrg(sysOrgAdminOrgList);
        }
        cloudUserRoleService.deleteOrgAdminRole(assignOrgRoleVo.getUserId());
        if (assignOrgRoleVo.getRoleIdList() != null && assignOrgRoleVo.getRoleIdList().size() > 0) {
            List<CloudUserRole> sysOrgAdminRoleList = new ArrayList<>();
            for (Long roleId : assignOrgRoleVo.getRoleIdList()) {
                CloudUserRole sysOrgAdminRole = new CloudUserRole();
                sysOrgAdminRole.setUserId(assignOrgRoleVo.getUserId());
                sysOrgAdminRole.setRoleId(roleId);
                sysOrgAdminRoleList.add(sysOrgAdminRole);
            }
            cloudUserRoleService.saveOrgAdminRole(sysOrgAdminRoleList);
        }
        return true;
    }
}
