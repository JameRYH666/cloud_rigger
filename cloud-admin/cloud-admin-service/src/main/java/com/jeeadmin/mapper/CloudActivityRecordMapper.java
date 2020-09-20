package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudActivityRecord;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.vo.activity.CloudActivityRecordVo;
import com.jeerigger.frame.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      活动记录mapper接口
 * @date 2020/9/8
**/
public interface CloudActivityRecordMapper extends BaseMapper<CloudActivityRecord> {

    /**
    * @Author: Ryh
    * @Description:     通过活动记录ID查询到所有的附件信息
    * @Param: [null]
    * @Date: Create in 2020/9/13
    * @Return: null
    * @Throws:
    */
    List<CloudEnclosure> selectEnclosuresByActivityRecordId(Long id);

    /**
    * @Author: Ryh
    * @Description:     查询单个活动记录的详细信息
    * @Param: [null]
    * @Date: Create in 2020/9/13
    * @Return: null
    * @Throws:
    */
    CloudActivityRecordVo selectActivityRecordDetail(Long id);

    /**
     *  查询所有活动记录
     */
    @Select("select * from cloud_activity ca where ca.activity_status != #{recordStatus} ")
    List<CloudActivityRecord> selectAllActivityRecord(String recordStatus);

}