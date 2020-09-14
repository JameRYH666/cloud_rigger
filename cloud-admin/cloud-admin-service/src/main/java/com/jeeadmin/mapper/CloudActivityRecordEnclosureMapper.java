package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudActivityRecordEnclosure;
import com.jeerigger.frame.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
 * @author Seven Lee
 * @description
 *      活动记录附件mapper接口
 * @date 2020/9/8
**/
public interface CloudActivityRecordEnclosureMapper extends BaseMapper<CloudActivityRecordEnclosure> {

    boolean deleteRecordEnclosureByActivityRecordId(Long id);

}