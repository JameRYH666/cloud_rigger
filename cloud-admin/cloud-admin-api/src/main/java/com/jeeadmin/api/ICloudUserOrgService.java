package com.jeeadmin.api;

import com.jeeadmin.entity.CloudUserOrg;
import com.jeerigger.frame.base.service.BaseService;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      组织机构管理员  组织机构分配表 服务类
 * @date 2020/9/8
**/
public interface ICloudUserOrgService extends BaseService<CloudUserOrg> {
    /**
     * 保存机构管理员分配的可管理机构
     *
     * @param sysOrgAdminOrgList
     * @return
     */
    boolean saveOrgAdminOrg(List<CloudUserOrg> sysOrgAdminOrgList);

    /**
     * 删除机构管理员已分配的可管理机构
     *
     * @param userId
     * @return
     */
    boolean deleteOrgAdminOrg(Long userId);

    /**
     * 查看机构管理员已分配的可管理的组织机构
     *
     * @param userId
     * @return
     */
    List<CloudUserOrg> detailOrgList(Long userId);


}
