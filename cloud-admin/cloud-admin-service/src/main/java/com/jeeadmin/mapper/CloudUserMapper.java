package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudUser;
import com.jeerigger.frame.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      管理员mapper接口
 * @date 2020/9/8
**/
public interface CloudUserMapper extends BaseMapper<CloudUser> {

    List<CloudUser> selectNotPartyMember(@Param("orgIds") List<String> orgIds);

    /**
     *  根据当前用户id查询上级党员id
     */
    long selectPartyMemberIdByUserId(Long id);

}