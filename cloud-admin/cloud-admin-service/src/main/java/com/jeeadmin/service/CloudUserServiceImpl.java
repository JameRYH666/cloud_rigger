package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudOrgService;
import com.jeeadmin.api.ICloudPartyMemberService;
import com.jeeadmin.api.ICloudUserOrgService;
import com.jeeadmin.api.ICloudUserService;
import com.jeeadmin.entity.CloudMenu;
import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.entity.CloudUser;
import com.jeeadmin.entity.CloudUserOrg;
import com.jeeadmin.mapper.CloudUserMapper;
import com.jeeadmin.vo.user.QueryUserVo;
import com.jeeadmin.vo.user.UpdatePwdVo;
import com.jeeadmin.vo.user.UpdateUserVo;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.core.module.sys.SysConstant;
import com.jeerigger.core.module.sys.entity.UserMenu;
import com.jeerigger.core.module.sys.service.IUserService;
import com.jeerigger.core.module.sys.util.SysParamUtil;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.StatusEnum;
import com.jeerigger.frame.enums.UserTypeEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.KeysUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import com.jeerigger.security.user.JeeUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.text.resources.FormatData;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Seven Lee
 * @description 系统管理员信息表 服务实现类
 * @date 2020/9/8
 **/
@Service
public class CloudUserServiceImpl extends BaseServiceImpl<CloudUserMapper, CloudUser> implements ICloudUserService {
    @Autowired
    private IUserService userService;

    @Autowired
    private CloudUserMapper cloudUserMapper;

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private ICloudOrgService cloudOrgServiceImpl;
    @Autowired
    private ICloudUserOrgService cloudUserOrgServiceImpl;
    @Autowired
    private ICloudPartyMemberService cloudPartyMemberServiceImpl;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public CloudUser getAdminUserByLoginName(String loginName) {
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(CloudUser::getLoginName, loginName);
        return this.getOne(queryWrapper);
    }

    @Override
    public Page<CloudUser> selectPage(PageHelper<QueryUserVo> pageHelper) {
        Page page = new Page<CloudUser>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper();
        if (pageHelper.getData() != null) {
            QueryUserVo queryUserVo = pageHelper.getData();
            queryWrapper.lambda().eq(CloudUser::getMgrType, UserTypeEnum.SYSTEM_ADMIN_USER.getCode());
            if (StringUtil.isNotEmpty(queryUserVo.getLoginName())) {
                queryWrapper.lambda().like(CloudUser::getLoginName, queryUserVo.getLoginName());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getLoginName())) {
                queryWrapper.lambda().like(CloudUser::getLoginName, queryUserVo.getLoginName());
            }
            if (StringUtil.isNotEmpty(queryUserVo.getUserStatus())) {
                queryWrapper.lambda().like(CloudUser::getUserStatus, queryUserVo.getUserStatus());
            }
        }
        queryWrapper.lambda().orderByAsc(CloudUser::getUserSort);
        return (Page<CloudUser>) this.page(page, queryWrapper);
    }

    @Override
    public boolean saveAdminUser(CloudUser cloudUser) {
        if (Objects.isNull(cloudUser)){
            throw new ValidateException("没有获取到新增用户信息");
        }

        // cloudUser.setMgrType(UserTypeEnum.SYSTEM_ADMIN_USER.getCode());
        //校验数据准确性
        ValidateUtil.validateObject(cloudUser);
        //验证登录名
        validateLoginName(cloudUser);
        // 验证邮箱的唯一性
        validateUserEmail(cloudUser);
        // 验证手机号的唯一性
        validateUserNumber(cloudUser);
        validateUserCertificateNumber(cloudUser);
        //cloudUser.setPassword(StringUtil.md5(SysParamUtil.getInitPassword()));
        if (Objects.isNull(cloudUser.getOrgId())){
            throw new ValidateException("党组织id不能为空");
        }
        Long orgId = cloudUser.getOrgId();


        // todo 暂时默认密码写死
        cloudUser.setPassword("123456");
        cloudUser.setUserStatus(StatusEnum.NORMAL.getCode());
        long id = snowFlake.nextId();
        cloudUser.setId(id);
        //todo  security暂时获取不到数据
        // cloudUser.setCreateUser(SecurityUtil.getUserId());
        cloudUser.setCreateUser(1L);
        if (this.save(cloudUser)){
            CloudUserOrg cloudUserOrg = new CloudUserOrg();
            cloudUserOrg.setOrgId(orgId)
            .setUserId(id);
          return   cloudUserOrgServiceImpl.saveOrgUser(cloudUserOrg);
        }
        throw new ValidateException("新增用户信息失败");
    }

    @Override
    public boolean updateAdminUser(CloudUser sysAdminUser) {
        if (this.getById(sysAdminUser.getId()) == null) {
            throw new ValidateException("该管理员（" + sysAdminUser.getLoginName() + "）已不存在，不能进行编辑！");
        }
        //校验数据准确性
        ValidateUtil.validateObject(sysAdminUser);
        //验证登录名
        validateLoginName(sysAdminUser);
        validateUserNumber(sysAdminUser);
        validateUserEmail(sysAdminUser);
        sysAdminUser.setMgrType(UserTypeEnum.SYSTEM_ADMIN_USER.getCode());
        return this.updateById(sysAdminUser);
    }

    @Override
    public boolean updateAdminUserStatus(CloudUser sysAdminUser) {
        if (Objects.isNull(sysAdminUser.getId()) || StringUtil.isEmpty(sysAdminUser.getUserStatus())) {
            throw new ValidateException("用户唯一标识和状态不能为空！");
        }
        if (this.getById(sysAdminUser.getId()) == null) {
            throw new ValidateException("用户不存在！");
        }
        CloudUser adminUser = new CloudUser();
        adminUser.setId(sysAdminUser.getId());
        adminUser.setUserStatus(sysAdminUser.getUserStatus());

        return this.updateById(adminUser);
    }


    /**
     * 保存、更新验证登录用户名是否存在
     *
     * @param sysAdminUser
     */
    private void validateLoginName(CloudUser sysAdminUser) {
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper<CloudUser>();
        queryWrapper.lambda().eq(CloudUser::getLoginName, sysAdminUser.getLoginName());
        if (Objects.nonNull(sysAdminUser.getId())) {
            queryWrapper.lambda().ne(CloudUser::getId, sysAdminUser.getId());
        }
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("该登录名（" + sysAdminUser.getLoginName() + "）已存在！");
        }
    }
    /**
     * 验证用户手机号
     *
     * @param sysUser
     */
    private void validateUserNumber(CloudUser sysUser) {
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(CloudUser::getPhoneNumber, sysUser.getPhoneNumber());
        if (Objects.nonNull(sysUser.getId())) {
            queryWrapper.lambda().ne(CloudUser::getId, sysUser.getId());
        }
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("用户手机号已存在！");
        }
    }
    /**
     * 验证用户身份证号
     *
     * @param sysUser
     */
    private void validateUserCertificateNumber(CloudUser sysUser) {
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(CloudUser::getCertificateNumber, sysUser.getCertificateNumber());
        if (Objects.nonNull(sysUser.getId())) {
            queryWrapper.lambda().ne(CloudUser::getId, sysUser.getId());
        }
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("用户手机号已存在！");
        }
    }

    /**
     * 验证用户邮箱号
     *
     * @param sysUser
     */
    private void validateUserEmail(CloudUser sysUser) {
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(CloudUser::getEmail, sysUser.getEmail());
        if (Objects.nonNull(sysUser.getId())) {
            queryWrapper.lambda().ne(CloudUser::getId, sysUser.getId());
        }
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("用户邮箱已存在！");
        }
    }
    @Override
    public boolean resetPwd(Long userId) {
        CloudUser sysAdminUser = new CloudUser();
        sysAdminUser.setPassword(StringUtil.md5(SysParamUtil.getInitPassword()));
        sysAdminUser.setId(userId);
        return this.updateById(sysAdminUser);
    }

    @Override
    public boolean changePassword(UpdatePwdVo updatePwdVo) {



        //todo SecurityUtil.getUserId()
        CloudUser sysAdminUser = this.getById(1L);
        if (sysAdminUser != null) {
            if (sysAdminUser.getPassword().equals(StringUtil.md5(updatePwdVo.getOldPassword()))) {
                sysAdminUser = new CloudUser();
                sysAdminUser.setId(SecurityUtil.getUserId());
                sysAdminUser.setPassword(StringUtil.md5(updatePwdVo.getNewPassword()));
                return this.updateById(sysAdminUser);
            } else {
                throw new ValidateException("输入的老密码不正确！");
            }
        } else {
            throw new FrameException(ResultCodeEnum.ERROR_NO_USER_INFO);
        }
    }

    @Override
    public boolean updateUserInfo(UpdateUserVo updateUserVo) {
        CloudUser sysAdminUser = new CloudUser();
        BeanUtils.copyProperties(updateUserVo, sysAdminUser);
        sysAdminUser.setId(SecurityUtil.getUserId());
        if (SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
            sysAdminUser.setMgrType(UserTypeEnum.SUPER_ADMIN_USER.getCode());
        }
        return this.updateById(sysAdminUser);
    }

    @Override
    public List<CloudMenu> getSysAdminMenu() {
        List<UserMenu> userMenuList = null;
        if (SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SUPER_ADMIN_USER)) {
            userMenuList = userService.getSuperAdminMenu();
        } else if (SecurityUtil.getUserData().getUserType().equals(UserTypeEnum.SYSTEM_ADMIN_USER)) {
            userMenuList = userService.getAdminUserMenu(SysConstant.SYS_ADMIN_ROLE);
        }
        List<CloudMenu> sysMenuList = new ArrayList<CloudMenu>();
        if (userMenuList != null && userMenuList.size() > 0) {
            for (UserMenu userMenu : userMenuList) {
                CloudMenu sysMenu = new CloudMenu();
                BeanUtils.copyProperties(userMenu, sysMenu);
                sysMenuList.add(sysMenu);
            }
        }
        return sysMenuList;
    }

    /**
     * 获取非党员的用户
     */
    @Override
    public List<CloudUser> selectNotPartyMember() {
        // todo 获取当前登录用户的信息
       //  JeeUser jeeUser = SecurityUtil.getUserData();
        //获取当前用户所在组织
        ArrayList<Long> userIds = new ArrayList<>();
        ArrayList<CloudUser> cloudUsers = new ArrayList<>();
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper<>();

        CloudUserOrg cloudUserOrg = cloudUserOrgServiceImpl.selectOrgByUserId();
        if (Objects.isNull(cloudUserOrg)){
            throw new ValidateException("没有获取到该党员的党组织信息");
        }



        List<CloudUserOrg> cloudUserOrgs = cloudUserOrgServiceImpl.selectOrgByOrgId(cloudUserOrg.getOrgId());
        for (CloudUserOrg userOrg : cloudUserOrgs) {
            CloudPartyMember member = cloudPartyMemberServiceImpl.getPartyMemberByUserId(userOrg.getUserId());
            if (Objects.isNull(member) ){
                userIds.add(userOrg.getUserId());
            }

        }
       if (Objects.nonNull(userIds)&&userIds.size()>0){
           for (Long userId : userIds) {
               CloudUser cloudUser  =    this.selectCloudUser(userId);

               cloudUsers.add(cloudUser);
           }
       }
       if (Objects.nonNull(cloudUsers) && cloudUsers.size()>0){
           return cloudUsers;
       }
        throw new ValidateException("该党支部没有不是党员的用户");
    }

    @Override
    public boolean updateUserPassword(UpdatePwdVo updatePwdVo) {

        QueryWrapper<CloudUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CloudUser::getEmail,updatePwdVo.getEmail());
        //通过邮箱查询用户
        CloudUser cloudUser = this.getOne(wrapper);
        //查不到说明用户不存在
        if (null != cloudUser){
            //从session中取出用户的邮箱验证码
            String key = String.format(KeysUtil.MAIL_CODE_KEY, updatePwdVo.getEmail());
            Object codeKey = httpSession.getAttribute(key);
            if (null != codeKey){
                if (null != updatePwdVo.getImgCode() && updatePwdVo.getImgCode().equals(codeKey)){
                    UpdateWrapper<CloudUser> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.lambda().set(CloudUser::getPassword,updatePwdVo.getNewPassword()).eq(CloudUser::getEmail,updatePwdVo.getEmail());
                    this.update(new CloudUser(),updateWrapper);
                }else {
                    throw new FrameException(ResultCodeEnum.ERROR_KAPTCHA_WRONG);
                }
                return true;
            }else {
                throw new FrameException(ResultCodeEnum.ERROR_KAPTCHA_FAILURE);
            }
        }else {
            throw new FrameException(ResultCodeEnum.ERROR_NO_EMAIL);
        }
    }

    @Override
    public CloudUser selectCloudUser(Long userId) {
        QueryWrapper<CloudUser> queryWrapper = new QueryWrapper<>();
        if (Objects.isNull(userId)){
            throw  new ValidateException("用户id不能为空");
        }
        queryWrapper.lambda().eq(CloudUser::getId,userId);
        CloudUser cloudUser = this.getOne(queryWrapper);
        if (Objects.isNull(cloudUser)){
            throw new ValidateException("用户的信息为空");
        }
        return cloudUser;
    }
}
