package com.jeeadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ISysMonitorService;
import com.jeeadmin.mapper.SysMonitorMapper;
import com.jeeadmin.vo.monitor.MsaOrgUserVo;
import com.jeeadmin.vo.monitor.MsaRoleUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMonitorServiceImpl implements ISysMonitorService {
    @Autowired
    private SysMonitorMapper monitorMapper;

    @Override
    public List<MsaOrgUserVo> selectMsaOrgUser() {
        return monitorMapper.selectMsaOrgUserPage(new Page(0, 5)).getRecords();
    }

    @Override
    public List<MsaRoleUserVo> selectMsaRoleUser() {
        return monitorMapper.selectMsaRoleUserPage(new Page(0, 5)).getRecords();
    }
}
