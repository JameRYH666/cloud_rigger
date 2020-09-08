package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudParam;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

/**
 * @author Seven Lee
 * @description
 *      系统参数配置表 服务类
 * @date 2020/9/8
**/
public interface ICloudParamService extends BaseService<CloudParam> {
    /**
     * 查询系统参数配置列表
     *
     * @param pageHelper
     * @return
     */
    Page<CloudParam> selectPage(PageHelper<CloudParam> pageHelper);

    /**
     * 保存系统参数
     *
     * @param sysParam
     * @return
     */
    CloudParam saveSysParam(CloudParam sysParam);

    /**
     * 更新系统参数
     *
     * @param sysParam
     * @return
     */
    boolean updateSysParam(CloudParam sysParam);

    /**
     * 删除系统参数
     *
     * @param paramId
     * @return
     */
    boolean deleteSysParam(Long paramId);

}
