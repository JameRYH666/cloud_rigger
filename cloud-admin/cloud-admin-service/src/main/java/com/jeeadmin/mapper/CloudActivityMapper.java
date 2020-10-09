package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudActivity;
import com.jeerigger.frame.base.mapper.BaseMapper;
import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      活动mapper接口
 * @date 2020/9/8
**/
public interface CloudActivityMapper extends BaseMapper<CloudActivity> {

    /**
     *      根据用户ID查询已经发起的活动
     */
    List<CloudActivity> selectByUserId(Long id);

    /**
     *      根据用户id查询已处理的活动
     */
    List<CloudActivity> selectProcessed(Long id);

    /**
     *      根据用户id查询未处理的活动
     */
    List<CloudActivity> selectUntreated(Long id);

    /**
     *      根据党员ID查询需要处理的活动
     */
    List<CloudActivity> selectPartyMemberByUserId(Long id);
}