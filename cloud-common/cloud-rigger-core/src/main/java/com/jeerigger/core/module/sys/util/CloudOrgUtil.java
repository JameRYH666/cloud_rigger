package com.jeerigger.core.module.sys.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeerigger.core.module.sys.entity.CloudOrg;
import com.jeerigger.core.module.sys.mapper.OrgMapper;
import com.jeerigger.frame.enums.StatusEnum;
import com.jeerigger.frame.support.util.SpringUtil;

import java.util.List;

/**
 * 机构工具类
 */
public class CloudOrgUtil {
    private static final String SYS_ORG_LIST = "SYS_ORG_LIST";



    private static OrgMapper getOrgMapper() {
        return SpringUtil.getBean(OrgMapper.class);
    }

    /**
     * 获取机构列表
     *
     * @return
     */
    public static List<CloudOrg> getSysOrgList() {
//        List<DictData> dictDataList= (List<DictData>)CacheUtil.getSysCache(SYS_DICT_DATA_LIST+"_"+dictType);
        QueryWrapper<CloudOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_status", StatusEnum.NORMAL.getCode());
        queryWrapper.orderByAsc("parent_id", "org_sort");
        return getOrgMapper().selectList(queryWrapper);
    }

    /**
     * 根据机构UUID获取机构信息
     *
     * @param orgid
     * @return
     */
    public static CloudOrg getSysOrg(Long orgid) {
        return getOrgMapper().selectById(orgid);
    }

    /**
     * 根据机构编码获取机构信息
     *
     * @param orgCode
     * @return
     */
    public static CloudOrg getSysOrgByCode(String orgCode) {
        QueryWrapper<CloudOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_code", orgCode);
        return getOrgMapper().selectOne(queryWrapper);
    }

    /**
     * 根据机构UUID获取机构名称
     *
     * @param orgid
     * @return
     */
    public static String getOrgName(Long orgid) {
        CloudOrg sysOrg = getSysOrg(orgid);
        if (sysOrg != null) {
            return sysOrg.getOrgName();
        } else {
            return "";
        }
    }

    /**
     * 根据机构编码获取机构名称
     *
     * @param orgCode
     * @return
     */
    public static String getOrgNameByCode(String orgCode) {
        CloudOrg sysOrg = getSysOrgByCode(orgCode);
        if (sysOrg != null) {
            return sysOrg.getOrgName();
        } else {
            return "";
        }
    }
}
