package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudActivityEnclosure;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeerigger.frame.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Seven Lee
 * @description 活动附件mapper接口
 * @date 2020/9/8
 **/
public interface CloudActivityEnclosureMapper extends BaseMapper<CloudActivityEnclosure> {


    @Select("select ce.id, ce.md5_code, ce.enclosure_name, ce" +
            ".enclosure_path,ce.enclosure_type from " +
            "cloud_activity_enclosure cae left join  cloud_enclosure ce on cae.enclosure_id = ce.id where cae" +
            ".activity_id =#{activityId}")
    List<CloudEnclosure> findEnclosuresByActivityId(Long activityId);
}