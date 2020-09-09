package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudUserService;
import com.jeeadmin.entity.CloudMenu;
import com.jeeadmin.entity.CloudUser;
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
import com.jeerigger.frame.enums.UserTypeEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //验证登录名
        validateLoginName(cloudUser);
        // cloudUser.setMgrType(UserTypeEnum.SYSTEM_ADMIN_USER.getCode());
        //校验数据准确性
        ValidateUtil.validateObject(cloudUser);
        //cloudUser.setPassword(StringUtil.md5(SysParamUtil.getInitPassword()));
        // todo 暂时默认密码写死
        cloudUser.setPassword("123456");
        long id = snowFlake.nextId();
        cloudUser.setId(id);
        return this.save(cloudUser);
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

    @Override
    public boolean resetPwd(Long userId) {
        CloudUser sysAdminUser = new CloudUser();
        sysAdminUser.setPassword(StringUtil.md5(SysParamUtil.getInitPassword()));
        sysAdminUser.setId(userId);
        return this.updateById(sysAdminUser);
    }

    @Override
    public boolean changePassword(UpdatePwdVo updatePwdVo) {
        CloudUser sysAdminUser = this.getById(SecurityUtil.getUserId());
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
}
