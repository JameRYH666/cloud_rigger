package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudRole;
import com.jeeadmin.entity.CloudUser;
import com.jeeadmin.vo.user.QueryUserVo;
import com.jeeadmin.vo.role.AssignMenuVo;
import com.jeeadmin.vo.role.AssignUserVo;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      角色表 服务类
 * @date 2020/9/8
**/
public interface ICloudRoleService extends BaseService<CloudRole> {
    /**
     * 查询系统角色列表
     *
     * @param pageHelper
     * @return
     */
    Page<CloudRole> selectPage(PageHelper<CloudRole> pageHelper);

    /**
     * 更新角色状态
     *
     * @param roleId   角色唯一标识
     * @param roleStatus 状态 0:正常 2:停用
     * @return
     */
    boolean updateStatus(Long roleId, String roleStatus);

    /**
     * 保存角色
     *
     * @param sysRole
     * @return
     */
    CloudRole saveSysRole(CloudRole sysRole);

    /**
     * 更新角色
     *
     * @param sysRole
     * @return
     */
    boolean updateSysRole(CloudRole sysRole);

    /**
     * 删除角色
     *
     * @param roleId 角色唯一标识
     * @return
     */
    boolean deleteSysRole(Long roleId);

    /**
     * 获取用户可分配角色
     * @return
     */
    List<CloudRole> selectRoleList();
    /**
     * 获取用户列表
     * @param pageHelper
     * @return
     */
    Page<CloudUser> selectUserPage(PageHelper<QueryUserVo> pageHelper);
    /**
     * 角色分配用户
     * @param assignUserVo
     * @return
     */
    boolean assignUser(AssignUserVo assignUserVo);

    /**
     * 角色分配菜单
     * @param assignMenuVo
     * @return
     */
    boolean assignMenu(AssignMenuVo assignMenuVo);

}
