package com.jeeadmin.service;


import com.jeeadmin.api.ICloudUserOrgService;
import com.jeeadmin.api.ICloudUserRoleService;
import com.jeeadmin.api.ICloudUserService;
import com.jeeadmin.api.ICloudMenuService;
import com.jeeadmin.entity.CloudMenu;
import com.jeeadmin.entity.CloudUser;
import com.jeeadmin.entity.CloudUserOrg;
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
 * @description 管理员详情服务类实现
 * @date 2020/9/9
 **/
@Service
public class CloudUserDetailsServiceImpl implements JeeUserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICloudUserService cloudUserService;

    @Autowired
    private ICloudUserRoleService cloudUserRoleService;

    @Autowired
    private ICloudUserOrgService cloudUserOrgService;

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
        if (baseUser.getMgrType().equals(UserTypeEnum.SYSTEM_ADMIN_USER.getCode())) {
            // 超级管理员
            sysMenuList = sysMenuService.findAdminUserSysMenu(baseUser.getId(), UserTypeEnum.SYSTEM_ADMIN_USER.getCode());
            userTypeEnum = UserTypeEnum.SYSTEM_ADMIN_USER;
        }
        //设置组织id
        List<CloudUserOrg> cloudUserOrgs = cloudUserOrgService.detailOrgList(baseUser.getId());
        List<String> ordIds = new ArrayList<>();
        for (CloudUserOrg cloudUserOrg : cloudUserOrgs) {
            ordIds.add(String.valueOf(cloudUserOrg.getOrgId()));
        }
        //设置角色名称
        for (CloudMenu sysMenu : sysMenuList) {
            String permission = StringUtil.isEmpty(sysMenu.getPermission()) ? sysMenu.getMenuName() : sysMenu.getPermission();
            auths.add(new SimpleGrantedAuthority(permission));
        }
        return new JeeUser(baseUser.getId(), baseUser.getLoginName(), baseUser.getLoginName(), baseUser.getPassword(),
                ordIds, new ArrayList<>(), userTypeEnum, "0".equals(baseUser.getUserStatus()), true,
                true, !"3".equals(baseUser.getUserStatus()), auths);
    }
}
