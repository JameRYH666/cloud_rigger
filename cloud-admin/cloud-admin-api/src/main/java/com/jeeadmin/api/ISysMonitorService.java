package com.jeeadmin.api;

import com.jeeadmin.vo.monitor.MsaOrgUserVo;
import com.jeeadmin.vo.monitor.MsaRoleUserVo;

import java.util.List;
import java.util.Map;

/**
 * 系统监控
 */
public interface ISysMonitorService {
    /**
     * 获取部门人员占比分析
     *
     * @return
     */
    List<MsaOrgUserVo> selectMsaOrgUser();

    /**
     * 获取角色人员占比分析
     *
     * @return
     */
    List<MsaRoleUserVo> selectMsaRoleUser();

}
