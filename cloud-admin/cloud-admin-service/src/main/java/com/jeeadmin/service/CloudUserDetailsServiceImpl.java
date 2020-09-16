package com.jeeadmin.service;


import com.jeeadmin.api.ICloudUserService;
import com.jeeadmin.api.ICloudMenuService;
import com.jeeadmin.entity.CloudMenu;
import com.jeeadmin.entity.CloudUser;
import com.jeerigger.frame.enums.UserTypeEnum;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.service.JeeUserDetailsService;
import com.jeerigger.security.user.JeeUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Seven Lee
 * @description
 *      管理员详情服务类实现
 * @date 2020/9/9
**/
@Service
public class CloudUserDetailsServiceImpl implements JeeUserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICloudUserService cloudUserService;

    @Autowired
    private ICloudMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        logger.debug("权限框架-加载用户");
        List<GrantedAuthority> auths = new ArrayList<>();
        CloudUser baseUser = cloudUserService.getAdminUserByLoginName(loginName);
        if (baseUser == null) {
            logger.debug("找不到该用户 用户名:{}", loginName);
            throw new UsernameNotFoundException("找不到该用户！");
        }
        if ("2".equals(baseUser.getUserStatus())) {
            logger.debug("用户被禁用，无法登陆 用户名:{}", loginName);
            throw new UsernameNotFoundException("用户被禁用！");
        }
        List<CloudMenu> sysMenuList = new ArrayList<CloudMenu>();
        // 判断是否是超级管理员
        UserTypeEnum userTypeEnum = null;
        if (baseUser.getMgrType().equals(UserTypeEnum.SUPER_ADMIN_USER.getCode())) {
            // 超级管理员
            sysMenuList = sysMenuService.findAdminUserSysMenu(baseUser.getId(),UserTypeEnum.SUPER_ADMIN_USER.getCode());
            userTypeEnum = UserTypeEnum.SUPER_ADMIN_USER;
        } else if (baseUser.getMgrType().equals(UserTypeEnum.SYSTEM_ADMIN_USER.getCode())) {
            // 系统管理员
            sysMenuList = sysMenuService.findAdminUserSysMenu(baseUser.getId(),UserTypeEnum.SYSTEM_ADMIN_USER.getCode());
            userTypeEnum = UserTypeEnum.SYSTEM_ADMIN_USER;
        }
        //设置角色名称
        for (CloudMenu sysMenu : sysMenuList) {
            String permission = StringUtil.isEmpty(sysMenu.getPermission()) ? sysMenu.getMenuName() : sysMenu.getPermission();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            auths.add(authority);
        }

        return new JeeUser(baseUser.getId(), baseUser.getLoginName(), baseUser.getLoginName(), baseUser.getPassword(),
                new ArrayList<>(),new ArrayList<>(),userTypeEnum, "0".equals(baseUser.getUserStatus()), true,
                true, !"3".equals(baseUser.getUserStatus()), auths);

    }

}
