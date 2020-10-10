package com.jeerigger.core.module.sys.service.impl;

import com.jeerigger.core.module.sys.entity.SysAdminUser;
import com.jeerigger.core.module.sys.entity.SysUser;
import com.jeerigger.core.module.sys.entity.UserMenu;
import com.jeerigger.core.module.sys.entity.UserRole;
import com.jeerigger.core.module.sys.mapper.UserMapper;
import com.jeerigger.core.module.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

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
    public List<UserMenu> getUserMenu(Long userId) {
        return userMapper.getUserMenu(userId);
    }

    @Override
    public List<UserRole> getUserRole(Long userId) {
        return userMapper.getUserRole(userId);
    }
}
