package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudOrg;
import com.jeeadmin.vo.org.CloudOrgVo;
import com.jeerigger.frame.base.mapper.BaseMapper;
import com.jeerigger.frame.base.mapper.BaseTreeMapper;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      组织机构mapper接口
 * @date 2020/9/8
**/
public interface CloudOrgMapper extends BaseTreeMapper<CloudOrg> {
    List<CloudOrgVo> selectAllOrgDetail();
}