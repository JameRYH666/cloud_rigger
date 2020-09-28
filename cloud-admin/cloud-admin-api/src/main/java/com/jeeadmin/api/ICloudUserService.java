package com.jeeadmin.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudMenu;
import com.jeeadmin.entity.CloudUser;
import com.jeeadmin.vo.user.QueryUserVo;
import com.jeeadmin.vo.user.UpdatePwdVo;
import com.jeeadmin.vo.user.UpdateUserVo;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      系统管理员信息表 服务类
 * @date 2020/9/8
**/
public interface ICloudUserService extends BaseService<CloudUser> {
    /**
     * 根据登录名获取登录用户信息
     *
     * @param loginName
     * @return
     */
    CloudUser getAdminUserByLoginName(String loginName);

    /**
     * 查询系统管理员列表
     *
     * @param pageHelper 查询条件
     * @return
     */
    Page<CloudUser> selectPage(PageHelper<QueryUserVo> pageHelper);

    /**
     * 新增系统管理员数据
     *
     * @param sysAdminUser
     * @return
     */
    boolean saveAdminUser(CloudUser sysAdminUser);

    /**
     * 更新系统管理员信息
     *
     * @param sysAdminUser
     * @return
     */
    boolean updateAdminUser(CloudUser sysAdminUser);

    /**
     * 更新系统管理员信息
     *
     * @param sysAdminUser
     * @return
     */
    boolean updateAdminUserStatus(CloudUser sysAdminUser);

    /**
     * 管理员重置密码
     *
     * @param userId
     * @return
     */
    boolean resetPwd(Long userId);

    /**
     * 登录用户修改密码
     *
     * @param updatePwdVo
     * @return
     */
    boolean changePassword(UpdatePwdVo updatePwdVo);

    /**
     * 登录用户修改个人信息
     *
     * @param updateUserVo
     * @return
     */
    boolean updateUserInfo(UpdateUserVo updateUserVo);

    /**
     * 获取系统管理员菜单
     *
     * @return
     */
    List<CloudMenu> getSysAdminMenu();

    /**
     * 获取非党员的用户
     */
    List<CloudUser> selectNotPartyMember();

    /**
     * app端,用户忘记密码时，通过邮箱和验证码设置新的密码
     * @param updatePwdVo
     * @return
     */
    boolean updateUserPassword(UpdatePwdVo updatePwdVo);
    CloudUser selectCloudUser(Long userId);
}
