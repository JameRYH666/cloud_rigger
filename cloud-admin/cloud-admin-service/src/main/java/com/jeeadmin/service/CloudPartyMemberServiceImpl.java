package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudOrgService;
import com.jeeadmin.api.ICloudPartyMemberService;
import com.jeeadmin.api.ICloudUserOrgService;
import com.jeeadmin.api.ICloudUserRoleService;
import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.mapper.CloudPartyMemberMapper;
import com.jeeadmin.vo.user.AssignRoleVo;
import com.jeeadmin.vo.user.QueryUserVo;
import com.jeerigger.core.module.sys.util.SysOrgUtil;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Seven Lee
 * @description
 *      党员信息表 服务实现类
 * @date 2020/9/9
**/
@Service
public class CloudPartyMemberServiceImpl extends BaseServiceImpl<CloudPartyMemberMapper, CloudPartyMember> implements ICloudPartyMemberService {
    @Autowired
    private ICloudOrgService sysOrgService;
    @Autowired
    private ICloudUserRoleService sysUserRoleService;
    @Autowired
    private ICloudUserOrgService sysOrgAdminOrgService;
    @Autowired
    private ICloudUserRoleService sysOrgAdminRoleService;

    @Override
    public Page<CloudPartyMember> selectPage(PageHelper<QueryUserVo> pageHelper) {
        Page<CloudPartyMember> page = new Page<CloudPartyMember>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudPartyMember> queryWrapper = new QueryWrapper();
        if (pageHelper.getData() != null) {
            QueryUserVo queryUserVo = pageHelper.getData();
            if (StringUtil.isNotEmpty(queryUserVo.getUserEmail())) {
                queryWrapper.lambda().like(CloudPartyMember::getMemberEmail, queryUserVo.getUserEmail());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserMobile())) {
                queryWrapper.lambda().like(CloudPartyMember::getMemberPhoneNumber, queryUserVo.getUserMobile());
            }
            if (Objects.nonNull(queryUserVo.getOrgId())) {
                queryWrapper.lambda().eq(CloudPartyMember::getOrgId, queryUserVo.getOrgId());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserStatus())) {
                queryWrapper.lambda().eq(CloudPartyMember::getMemeberStatus, queryUserVo.getUserStatus());
            }
        }
        this.page(page, queryWrapper);
        for (CloudPartyMember sysUser : page.getRecords()) {
            if (sysUser.getOrgId() != null) {
                sysUser.setOrgName(SysOrgUtil.getOrgName(sysUser.getOrgId()));
            }
        }
        return page;
    }

    @Override
    public CloudPartyMember getUserById(Long userId) {
        if (Objects.isNull(userId)) {
            throw new ValidateException("用户ID不能为空！");
        }
        CloudPartyMember sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new ValidateException("用户不存在！");
        }
        if (sysUser != null && Objects.nonNull(sysUser.getOrgId())) {
            sysUser.setOrgName(sysOrgService.getById(sysUser.getOrgId()).getOrgName());
        }
        List<Long> postIdList = new ArrayList<>();
        /*List<SysUserPost> userPostList = sysUserPostService.detailPostList(userId);
        if (userPostList != null && userPostList.size() > 0) {
            for (SysUserPost sysUserPost : userPostList) {
                postIdList.add(sysUserPost.getPostId());
            }
        }
        sysUser.setPostIdList(postIdList);*/

        return sysUser;
    }

    @Override
    public List<CloudPartyMember> detailUserList(Long roleId) {
        return null;
    }

    @Override
    public boolean saveUser(CloudPartyMember sysUser) {
        return false;
    }

    @Override
    public boolean updateUser(CloudPartyMember sysUser) {
        return false;
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    @Override
    public boolean updateUserStatus(CloudPartyMember sysUser) {
        return false;
    }

    @Override
    public boolean resetUserPwd(Long id) {
        return false;
    }

    @Override
    public boolean assignRole(AssignRoleVo assignRoleVo) {
        return false;
    }


    /**
     * 验证党员姓名是否存在
     *
     * @param sysUser
     */
    private void validateLoginName(CloudPartyMember sysUser) {
        QueryWrapper<CloudPartyMember> queryWrapper = new QueryWrapper<CloudPartyMember>();
        queryWrapper.lambda().eq(CloudPartyMember::getMemberName, sysUser.getMemberName());
        if (Objects.nonNull(sysUser.getId())) {
            queryWrapper.lambda().ne(CloudPartyMember::getId, sysUser.getId());
        }
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("党员姓名已存在！");
        }
    }

    /**
     * 验证党员手机号
     *
     * @param sysUser
     */
    private void validateUserNumber(CloudPartyMember sysUser) {
        QueryWrapper<CloudPartyMember> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(CloudPartyMember::getMemberPhoneNumber, sysUser.getMemberPhoneNumber());
        if (Objects.nonNull(sysUser.getId())) {
            queryWrapper.lambda().ne(CloudPartyMember::getId, sysUser.getId());
        }
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("党员手机号已存在！");
        }
    }

 /*   @Override
    public List<CloudPartyMember> detailUserList(Long roleId) {
        return this.baseMapper.selectUserListByRoleId(roleId);
    }*/

   /* @Override
    public boolean saveUser(SysUser sysUser) {
        sysUser.setUserStatus(UserStatusEnum.NORMAL.getCode());
        sysUser.setMgrFlag(FlagEnum.NO.getCode());
        //验证数据
        ValidateUtil.validateObject(sysUser);
        //验证登录名
        validateLoginName(sysUser);
        //验证员工编号
        validateUserNumber(sysUser);
        if (sysOrgService.getById(sysUser.getOrgId()) == null) {
            throw new ValidateException("选择的组织机构代码已不存在！");
        }
        sysUser.setLoginPassword(StringUtil.md5(SysParamUtil.getInitPassword()));
        //保存用户信息
        boolean saveFlag = this.save(sysUser);
        if (saveFlag) {
            //保存岗位
            saveFlag = saveUserPost(sysUser.getId(), sysUser.getPostIdList());
            if (saveFlag) {
                //保存角色
                saveFlag = saveUserRole(sysUser.getId(), sysUser.getRoleIdList());
            }
        }
        return saveFlag;
    }

    @Override
    public boolean updateUser(SysUser sysUser) {
        if (this.getById(sysUser.getId()) == null) {
            throw new ValidateException("该用户不存在！");
        }
        //验证数据有效性
        ValidateUtil.validateObject(sysUser);
        //验证登录名
        validateLoginName(sysUser);
        //验证员工编号
        validateUserNumber(sysUser);

        //删除用户已分配的岗位
        if (sysUserPostService.deleteUserPost(sysUser.getId())) {
            saveUserPost(sysUser.getId(), sysUser.getPostIdList());
        }

        //更新用户信息
        return this.updateById(sysUser);
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (Objects.isNull(userId)) {
            throw new ValidateException("用户ID不能为空！");
        }
        //删除用户岗位信息
        sysUserPostService.deleteUserPost(userId);
        //删除用户角色关系表数据
        sysUserRoleService.deleteUserRole(userId);
        //机构管理员需删除机构管理员组织机构关系表
        sysOrgAdminOrgService.deleteOrgAdminOrg(userId);
        //机构管理员需删除机构管理员角色关系表
        sysOrgAdminRoleService.deleteOrgAdminRole(userId);
        //删除用户信息
        return this.removeById(userId);
    }


    @Override
    public boolean updateUserStatus(SysUser sysUser) {
        if (Objects.isNull(sysUser.getId()) || StringUtil.isEmpty(sysUser.getUserStatus())) {
            throw new ValidateException("用户唯一标识和状态不能为空！");
        }
        if (this.getById(sysUser.getId()) == null) {
            throw new ValidateException("用户不存在！");
        }
        SysUser user = new SysUser();
        user.setId(sysUser.getId());
        user.setUserStatus(sysUser.getUserStatus());

        return this.updateById(user);
    }

    @Override
    public boolean resetUserPwd(Long userId) {
        if (this.getById(userId) == null) {
            throw new ValidateException("用户已不存在！");
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setLoginPassword(StringUtil.md5(SysParamUtil.getInitPassword()));
        return this.updateById(sysUser);
    }

    @Override
    public boolean assignRole(AssignRoleVo assignRoleVo) {
        if (this.getById(assignRoleVo.getUserId()) == null) {
            throw new ValidateException("用户已不存在！");
        }
        //删除用户已分配的角色
        if (sysUserRoleService.deleteUserRole(assignRoleVo.getUserId())) {
            return saveUserRole(assignRoleVo.getUserId(), assignRoleVo.getRoleIdList());
        }
        return true;
    }

    *//**
     * 保存用户岗位
     *
     * @param userId
     * @param postIdList
     * @return
     *//*
    private boolean saveUserPost(Long userId, List<Long> postIdList) {
        if (postIdList != null && postIdList.size() > 0) {
            List<SysUserPost> sysUserPostList = new ArrayList<>();
            for (Long postId : postIdList) {
                SysUserPost sysUserPost = new SysUserPost();
                sysUserPost.setUserId(userId);
                sysUserPost.setPostId(postId);
                sysUserPostList.add(sysUserPost);
            }
            return sysUserPostService.saveUserPost(sysUserPostList);
        } else {
            return true;
        }
    }

    *//**
     * 保存用户已分配的角色
     *
     * @param userId
     * @param roleIdList
     * @return
     *//*
    private boolean saveUserRole(Long userId, List<Long> roleIdList) {
        if (roleIdList != null && roleIdList.size() > 0) {
            List<SysUserRole> sysUserRoleList = new ArrayList<>();
            for (Long roleId : roleIdList) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(roleId);
                sysUserRoleList.add(sysUserRole);
            }
            return sysUserRoleService.saveUserRole(sysUserRoleList);
        } else {
            return true;
        }
    }*/
}
