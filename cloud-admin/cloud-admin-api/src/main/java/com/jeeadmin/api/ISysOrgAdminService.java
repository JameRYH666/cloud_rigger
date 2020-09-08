package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.SysUser;
import com.jeeadmin.vo.orgadmin.AssignOrgRoleVo;
import com.jeeadmin.vo.orgadmin.QueryOrgRoleVo;
import com.jeeadmin.vo.user.QueryUserVo;
import com.jeerigger.frame.page.PageHelper;

/**
 * 机构管理员服务类
 */
public interface ISysOrgAdminService {
    /**
     * 查询组织机构管理员列表
     * @param pageHelper
     * @return
     */
    Page<SysUser> selectOrgAdminPage(PageHelper<QueryUserVo> pageHelper);
    /**
     * 获取用户列表
     * @param pageHelper
     * @return
     */
    Page<SysUser> selectUserPage(PageHelper<QueryUserVo> pageHelper);

    /**
     * 新增组织机构管理员
     * @param userId
     * @return
     */
    boolean saveOrgAdmin(Long userId);

    /**
     * 取消组织机构管理员
     * @param userUuid
     * @return
     */
    boolean cancelOrgAdmin(Long userId);

    /**
     * 查看机构管理员已分配的组织机构和角色
     * @param userUuid
     * @return
     */
    QueryOrgRoleVo detailOrgRole(Long userId);

    /**
     * 组织机构管理员 分配可管理的组织机构和角色
     * @param assignOrgRoleVo
     * @return
     */
    boolean assignOrgRole(AssignOrgRoleVo assignOrgRoleVo);
}
