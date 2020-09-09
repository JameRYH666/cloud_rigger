package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudLog;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

/**
 * @author Seven Lee
 * @description
 *      操作日志表 服务类
 * @date 2020/9/8
**/
public interface ICloudLogService extends BaseService<CloudLog> {
    /**
     * 获取日志列表
     *
     * @param pageHelper
     * @return
     */
    Page<CloudLog> selectPage(PageHelper<CloudLog> pageHelper);
}
