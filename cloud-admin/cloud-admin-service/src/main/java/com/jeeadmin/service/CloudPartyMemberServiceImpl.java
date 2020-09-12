package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudOrgService;
import com.jeeadmin.api.ICloudPartyMemberService;
import com.jeeadmin.api.ICloudUserOrgService;
import com.jeeadmin.api.ICloudUserRoleService;
import com.jeeadmin.entity.CloudOrg;
import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.mapper.CloudPartyMemberMapper;
import com.jeeadmin.vo.member.PartyMemberVo;
import com.jeeadmin.vo.user.AssignRoleVo;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.core.module.sys.util.CloudOrgUtil;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.UserStatusEnum;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Seven Lee
 * @description 党员信息表 服务实现类
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
    @Autowired
    private SnowFlake snowFlake;

    @Override
    public Page<CloudPartyMember> selectPage(PageHelper<PartyMemberVo> pageHelper) {
        Page<CloudPartyMember> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudPartyMember> queryWrapper = new QueryWrapper();
        if (pageHelper.getData() != null) {
            PartyMemberVo partyMemberVo = pageHelper.getData();
            if (StringUtil.isNotEmpty(partyMemberVo.getMemberEmail())) {
                queryWrapper.lambda().like(CloudPartyMember::getMemberEmail, partyMemberVo.getMemberEmail());
            }
            if (Objects.nonNull(partyMemberVo.getMemberPhoneNumber())) {
                queryWrapper.lambda().like(CloudPartyMember::getMemberPhoneNumber,
                        partyMemberVo.getMemberPhoneNumber());
            }
            if (Objects.nonNull(partyMemberVo.getOrgId())) {
                queryWrapper.lambda().eq(CloudPartyMember::getOrgId, partyMemberVo.getOrgId());
            }
            if (StringUtil.isNotEmpty(partyMemberVo.getMemberName())) {
                queryWrapper.lambda().like(CloudPartyMember::getMemberName, partyMemberVo.getMemberName());
            }
            if (StringUtil.isNotEmpty(partyMemberVo.getMemeberStatus())) {
                queryWrapper.lambda().eq(CloudPartyMember::getMemeberStatus, partyMemberVo.getMemeberStatus());
            }
        }
        this.page(page, queryWrapper);
        for (CloudPartyMember sysUser : page.getRecords()) {
            if (sysUser.getOrgId() != null) {
                sysUser.setOrgName(CloudOrgUtil.getOrgName(sysUser.getOrgId()));
            }
        }
        return page;
    }

    @Override
    public CloudPartyMember getPartyMemberById(Long userId) {
        if (Objects.isNull(userId)) {
            throw new ValidateException("用户ID不能为空！");
        }
        CloudPartyMember partyMember = this.getById(userId);
        if (partyMember == null) {
            throw new ValidateException("用户不存在！");
        }
        if (partyMember != null && Objects.nonNull(partyMember.getOrgId())) {
            partyMember.setOrgName(sysOrgService.getById(partyMember.getOrgId()).getOrgName());
        }
        return partyMember;
    }

    /**
     * @Author: Sgz
     * @Time: 9:05 2020/9/10
     * @Params: [orgName]
     * @Return: java.util.List<com.jeeadmin.entity.CloudPartyMember>
     * @Throws:
     * @Description: 根据党支部的名字查询该党支部的所有党员信息
     * todo 查询不到数据
     */
    @Override
    public Page<CloudPartyMember> detailPartyMemberList(PageHelper<CloudOrg> pageHelper) {
        QueryWrapper<CloudPartyMember> wrapper = new QueryWrapper<>();
        Page page = new Page<CloudPartyMember>(pageHelper.getCurrent(), pageHelper.getSize());
        if (pageHelper.getData() != null) {
            CloudOrg cloudOrg = pageHelper.getData();
            cloudOrg = sysOrgService.selectOrgByOrgName(pageHelper.getData().getOrgName());
            wrapper.lambda().eq(CloudPartyMember::getOrgId, cloudOrg.getId());
        }

        return (Page<CloudPartyMember>) this.page(page, wrapper);

    }

    @Override
    public List<CloudPartyMember> detailUserList(Long roleId) {

        /*List<CloudPartyMember> cloudPartyMemberList = new ArrayList<>();
        QueryWrapper<CloudPartyMember> queryWrapper = new QueryWrapper();
        CloudPartyMember cloudPartyMember = new CloudPartyMember();
        //  首先需要判断roleId是否已经获取到
        if(Objects.isNull(roleId)){
            //  如果roleId为空  就直接抛出异常，提示错误信息
            throw new ValidateException("党员的角色id不能为空");
        }else {
            //  如果roleId不为空，根据roleId查询党员详细列表
          queryWrapper.lambda().eq(Cloudpar)
            if (cloudPartyMembers ==null || cloudPartyMembers.size()>0){
                throw new ValidateException("没有改角色的党员信息");
            }

        }*/
        return null;
    }

    /**
     * @Author: Sgz
     * @Time: 14:26 2020/9/10
     * @Params: [cloudPartyMember]
     * @Return: boolean
     * @Throws:
     * @Description: 添加党员信息
     */

    @Override
    public boolean saveUser(CloudPartyMember cloudPartyMember) {
        // 利用雪花算法生成id
        cloudPartyMember.setId(snowFlake.nextId());
        cloudPartyMember.setCreateUser(SecurityUtil.getUserId());
        //验证数据
        ValidateUtil.validateObject(cloudPartyMember);

        //验证党员手机号
        validateUserNumber(cloudPartyMember);
        cloudPartyMember.setMemeberStatus(UserStatusEnum.NORMAL.getCode());

        //保存用户信息
        return this.save(cloudPartyMember);


    }

    /**
     * @Author: Sgz
     * @Time: 14:27 2020/9/10
     * @Params: [cloudPartyMember]
     * @Return: boolean
     * @Throws:
     * @Description: 更新党员信息
     */

    @Override
    public boolean updateUser(CloudPartyMember cloudPartyMember) {
        QueryWrapper<CloudPartyMember> queryWrapper = new QueryWrapper<>();
        if (this.getById(cloudPartyMember.getId()) == null) {
            throw new ValidateException("该用户不存在！");

        }
        //验证数据
        ValidateUtil.validateObject(cloudPartyMember);
        cloudPartyMember.setUpdateUser(SecurityUtil.getUserId());

        CloudPartyMember partyMember = this.getPartyMemberById(cloudPartyMember.getId());
        if (!partyMember.getMemberPhoneNumber().equals(cloudPartyMember.getMemberPhoneNumber())) {
            validateUserNumber(cloudPartyMember);
        }


        //更新用户信息
        return this.updateById(cloudPartyMember);

    }

    @Override
    public boolean deleteUser(Long id) {

        return false;
    }


    @Override
    public boolean updateUserStatus(CloudPartyMember sysUser) {
        return false;
    }

    /**
     * @param cloudPartyMember
     * @Author: Sgz
     * @Time: 15:02 2020/9/10
     * @Params: [cloudPartyMember]
     * @Return: boolean
     * @Throws:
     * @Description: 逻辑删除党员信息
     */
    @Override
    public boolean deleteUser(CloudPartyMember cloudPartyMember) {
        if (Objects.isNull(cloudPartyMember)) {
            throw new ValidateException("党员信息不能为空");
        }
        cloudPartyMember.setMemeberStatus(UserStatusEnum.REMOVE.getCode());
        return this.updateById(cloudPartyMember);

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
