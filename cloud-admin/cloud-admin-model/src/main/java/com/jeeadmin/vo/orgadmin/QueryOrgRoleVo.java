package com.jeeadmin.vo.orgadmin;

import com.jeeadmin.entity.CloudUserOrg;
import com.jeeadmin.entity.CloudUserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 获取组织机构管理员已分配的组织机构管理员和角色
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QueryOrgRoleVo {
    /**
     * 用户uuid
     */
    private Long userId;
    /**
     * 组织机构管理员已分配角色
     */
    private List<CloudUserRole>  OrgAdminRoleList;
    /**
     * 组织机构管理员已分配组织机构
     */
    private List<CloudUserOrg>  OrgAdminOrgList;
}
