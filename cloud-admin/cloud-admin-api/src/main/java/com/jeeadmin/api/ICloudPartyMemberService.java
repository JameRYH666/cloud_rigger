package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudOrg;
import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.vo.member.CloudPartyMemberVo;
import com.jeeadmin.vo.member.PartyMemberVo;
import com.jeeadmin.vo.user.AssignRoleVo;
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
     *
     * @param pageHelper
     * @return
     */
    Page<CloudPartyMember> selectPage(PageHelper<PartyMemberVo> pageHelper);

    /**
     * 根据用户唯一标识获取用户信息
     *
     * @param id
     * @return
     */
    CloudPartyMember getPartyMemberById(Long id);

    /**
     * 查看角色已分配用户
     *
     * @param roleId 暂时不用
     * @return
     */
    List<CloudPartyMember> detailUserList(Long roleId);

    /**
     * @Author: Sgz
     * @Time: 9:05 2020/9/10
     * @Params: [orgName]
     * @Return:
     * @Throws:
     * @Description: 根据党支部的名字查询党员信息
     */
    Page<CloudPartyMember> detailPartyMemberList(PageHelper<CloudOrg> pageHelper);

    /**
     * 新增用户信息
     *
     * @param sysUser
     * @return
     */
    boolean saveUser(CloudPartyMember sysUser);

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    boolean updateUser(CloudPartyMember sysUser);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    boolean deleteUser(Long id);

    /**
     * 更新用户状态
     *
     * @param sysUser
     * @return
     */
    boolean updateUserStatus(CloudPartyMember sysUser);

    /**
     * @Author: Sgz
     * @Time: 15:02 2020/9/10
     * @Params: [cloudPartyMember]
     * @Return: boolean
     * @Throws:
     * @Description: 逻辑删除党员信息
     */
    boolean deleteUser(CloudPartyMember cloudPartyMember);

    /**
     * 重置用户密码
     *
     * @param id
     * @return
     */
    boolean resetUserPwd(Long id);

    /**
     * 用户分配角色
     *
     * @param assignRoleVo
     * @return
     */
    boolean assignRole(AssignRoleVo assignRoleVo);

    /**
     * 根据登录用户的id查询党员个人信息
     * useId直接从security中获取
     * @param
     * @return
     */
    CloudPartyMemberVo selectPartyMemberByUserId();


}