package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudEnclosure;
import com.jeerigger.frame.base.mapper.BaseMapper;

/**
 * @author Seven Lee
 * @description
 *      附件mapper接口
 * @date 2020/9/8
**/
public interface CloudEnclosureMapper extends BaseMapper<CloudEnclosure> {

    /**
     *      根据活动记录id删除附件信息
     * @param id
     * @return
     */
    boolean deleteCloudEnclosureByActivityRecordId(Long id);

    /**
     *      通过活动id删除附件信息
     */
    boolean deleteCloudEnclosureByActivityId(Long id);

    boolean deleteEnclosure(Long meetingId);
}