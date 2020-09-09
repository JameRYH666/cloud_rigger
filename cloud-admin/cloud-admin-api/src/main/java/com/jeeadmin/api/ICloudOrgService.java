package com.jeeadmin.api;

import com.jeeadmin.entity.CloudOrg;
import com.jeerigger.frame.base.service.BaseTreeService;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      组织机构表 服务类
 * @date 2020/9/8
**/
public interface ICloudOrgService extends BaseTreeService<CloudOrg> {

    /**
     * 查询下级组织机构列表
     *
     * @param id
     * @return
     */
    List<CloudOrg> selectChildOrg(Long id);

    /**
     * 查询组织机构列表
     *
     * @param sysOrg
     * @return
     */
    List<CloudOrg> selectOrgList(CloudOrg sysOrg);

    /**
     * 查看组织机构详细信息
     *
     * @param id
     * @return
     */
    CloudOrg detailOrg(Long id);

    /**
     * 新增组织机构
     *
     * @param sysOrg
     * @return
     */
    CloudOrg saveSysOrg(CloudOrg sysOrg);

    /**
     * 更新组织机构信息
     *
     * @param sysOrg
     * @return
     */
    boolean updateSysOrg(CloudOrg sysOrg);

    /**
     * 修改组织机构状态
     *
     * @param sysOrg
     * @return
     */
    boolean updateStatus(CloudOrg sysOrg);

    /**
     * 删除组织机构
     *
     * @param id
     * @return
     */
    boolean deleteSysOrg(Long id);

}
