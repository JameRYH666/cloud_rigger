package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudRecord;
import com.jeeadmin.vo.record.CloudRecordVo;
import com.jeerigger.frame.base.mapper.BaseMapper;

import java.util.List;

/**
* @Author: ymz
* @Description:         外出报备mapper接口
* @Param: [null]
* @Date: Create in 2020/9/18
* @Return: null
* @Throws:
*/
public interface CloudRecordMapper extends BaseMapper<CloudRecord> {

    /**
     * 根据党员id查询该党员未被删除的所有外出报备数据
     * @param partyMemberId
     * @return
     */
    List<CloudRecordVo> selectAll(Long partyMemberId);

    /**
     * 根据党员id查询该党员待审核的外出报备数据
     * @param partyMemberId
     * @return
     */
    List<CloudRecordVo> selectNotReview(Long partyMemberId);

    /**
     * 根据党员id查询已处理的外出报备数据
     * @param partyMemberId
     * @return
     */
    List<CloudRecordVo> selectProcessed(Long partyMemberId);

    /**
     * 根据外出报备表的主键id查询该数据的详情
     * @param recordId
     * @return
     */
    CloudRecordVo getDetail(Long recordId);
}