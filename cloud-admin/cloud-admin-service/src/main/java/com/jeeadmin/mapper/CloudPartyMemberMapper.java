package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudPartyMember;
import com.jeerigger.frame.base.mapper.BaseMapper;

/**
 * @author Seven Lee
 * @description
 *      党员mapper接口
 * @date 2020/9/8
**/
public interface CloudPartyMemberMapper extends BaseMapper<CloudPartyMember> {

    /**
     * 根据党支部的id获取党支部的人数
     * @param orgId
     * @return
     */
    Integer selectCountByOrgId(Long orgId);
}