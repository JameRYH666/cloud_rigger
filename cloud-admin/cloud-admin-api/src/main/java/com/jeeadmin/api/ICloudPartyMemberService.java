package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.vo.user.AssignRoleVo;
import com.jeeadmin.vo.user.QueryUserVo;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      党员信息表 服务类
 * @date 2020/9/8
**/
public interface ICloudPartyMemberService extends BaseService<CloudPartyMember> {
    /**
     * 获取用户信息
     * @param pageHelper
     * @return
     */
    Page<CloudPartyMember> selectPage(PageHelper<QueryUserVo> pageHelper);

    /**
     * 根据用户唯一标识获取用户信息
     * @param id
     * @return
     */
    CloudPartyMember getUserById(Long id);
    /**
     * 查看角色已分配用户
     * @param roleId
     * @return
     */
    List<CloudPartyMember> detailUserList(Long roleId);
    /**
     * 新增用户信息
     * @param sysUser
     * @return
     */
    boolean saveUser(CloudPartyMember sysUser);

    /**
     * 更新用户信息
     * @param sysUser
     * @return
     */
    boolean updateUser(CloudPartyMember sysUser);

    /**
     * 删除用户
     * @param id
     * @return
     */
    boolean deleteUser(Long id);

    /**
     * 更新用户状态
     * @param sysUser
     * @return
     */
    boolean updateUserStatus(CloudPartyMember sysUser);

    /**
     * 重置用户密码
     * @param id
     * @return
     */
    boolean resetUserPwd(Long id);

    /**
     * 用户分配角色
     * @param assignRoleVo
     * @return
     */
    boolean assignRole(AssignRoleVo assignRoleVo);


}
