package com.jeerigger.core.module.sys.service.impl;

import com.jeerigger.core.common.properties.JeeRiggerProperties;
import com.jeerigger.core.module.sys.entity.SysAdminUser;
import com.jeerigger.core.module.sys.entity.SysUser;
import com.jeerigger.core.module.sys.entity.UserMenu;
import com.jeerigger.core.module.sys.entity.UserRole;
import com.jeerigger.core.module.sys.mapper.UserMapper;
import com.jeerigger.core.module.sys.service.IUserService;
import com.jeerigger.frame.enums.SysCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JeeRiggerProperties properties;

    @Override
    public SysAdminUser getSysAdminUser(String loginName) {
        return userMapper.getSysAdminUser(loginName);
    }

    @Override
    public SysUser getSysUser(String loginName) {
        return userMapper.getSysUser(loginName);
    }

    @Override
    public List<UserMenu> getAdminUserMenu(String role_code) {
        return userMapper.getAdminUserMenu(role_code);
    }

    @Override
    public List<UserMenu> getSuperAdminMenu() {
        return userMapper.getSuperAdminMenu();
    }

    @Override
    public List<UserMenu> getUserMenu(String userUuid) {
        List<String> sysCodeList = null;// properties.getShiro().getMenu_sys_code();
        if (sysCodeList == null || sysCodeList.size() < 1) {
            sysCodeList.add(SysCodeEnum.JEE_RIGGER_SYSTEM.getCode());
        }
        return userMapper.getUserMenu(userUuid, sysCodeList);
    }

    @Override
    public List<UserRole> getUserRole(String userUuid) {
        return userMapper.getUserRole(userUuid);
    }
}
