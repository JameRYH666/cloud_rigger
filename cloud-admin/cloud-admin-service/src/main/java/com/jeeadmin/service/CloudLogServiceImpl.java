package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudLogService;
import com.jeeadmin.entity.CloudLog;
import com.jeeadmin.mapper.CloudLogMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.util.StringUtil;
import org.springframework.stereotype.Service;

/**
 * @author Seven Lee
 * @description
 *      操作日志表 服务实现类
 * @date 2020/9/9
**/
@Service
public class CloudLogServiceImpl extends BaseServiceImpl<CloudLogMapper, CloudLog> implements ICloudLogService {

    @Override
    public Page<CloudLog> selectPage(PageHelper<CloudLog> pageHelper) {
        Page<CloudLog> page = new Page<CloudLog>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudLog> queryWrapper = new QueryWrapper<CloudLog>();
        if (pageHelper.getData() != null) {
            CloudLog sysLog = pageHelper.getData();
            if (StringUtil.isNotEmpty(sysLog.getLogTitle())) {
                queryWrapper.lambda().like(CloudLog::getLogTitle, sysLog.getLogTitle());
            }
            if (StringUtil.isNotEmpty(sysLog.getLogType())) {
                queryWrapper.lambda().like(CloudLog::getLogType, sysLog.getLogType());
            }
            if (StringUtil.isNotEmpty(sysLog.getRequestUri())) {
                queryWrapper.lambda().like(CloudLog::getRequestUri, sysLog.getRequestUri());
            }
            if (StringUtil.isNotEmpty(sysLog.getLoginName())) {
                queryWrapper.lambda().like(CloudLog::getLoginName, sysLog.getLoginName());
            }
            if (StringUtil.isNotEmpty(sysLog.getExceptionFlag())) {
                queryWrapper.lambda().eq(CloudLog::getExceptionFlag, sysLog.getExceptionFlag());
            }
            if (StringUtil.isNotEmpty(sysLog.getRemoteAddr())) {
                queryWrapper.lambda().eq(CloudLog::getRemoteAddr, sysLog.getRemoteAddr());
            }

            /*if (sysLog.getExecuteDateStart() != null && sysLog.getExecuteDateEnd() != null) {
                queryWrapper.lambda().between(SysLog::getCreateDate, sysLog.getExecuteDateStart(), sysLog.getExecuteDateEnd());
            } else {
                if (sysLog.getExecuteDateStart() != null) {
                    queryWrapper.lambda().ge(SysLog::getCreateDate, sysLog.getExecuteDateStart());
                }

                if (sysLog.getExecuteDateEnd() != null) {
                    queryWrapper.lambda().le(SysLog::getCreateDate, sysLog.getExecuteDateEnd());
                }
            }*/
        }
        queryWrapper.lambda().orderByDesc(CloudLog::getCreateDate);
        return (Page<CloudLog>) this.page(page, queryWrapper);
    }
}
