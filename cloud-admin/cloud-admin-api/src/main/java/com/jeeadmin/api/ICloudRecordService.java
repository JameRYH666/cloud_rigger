package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudRecord;
import com.jeeadmin.vo.record.CloudRecordVo;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

import java.util.List;

public interface ICloudRecordService extends BaseService<CloudRecord> {

    /**
     * 根据党员id查询该党员未被删除的所有外出报备数据
     * @param cloudRecord
     * @return
     */
    CloudRecord saveCloudRecord(CloudRecord cloudRecord);

    /**
     * 外出报备数据列表
     * @param pageHelper
     * @return
     */
    Page<CloudRecordVo> selectList(PageHelper<CloudRecord> pageHelper);

    /**
     * 根据党员id查询该党员待审核的外出报备数据
     * @param pageHelper
     * @return
     */
    Page<CloudRecordVo> selectNotReview(PageHelper<CloudRecord> pageHelper);

    /**
     * 根据党员id查询已处理的外出报备数据
     * @param pageHelper
     * @return
     */
    Page<CloudRecordVo> selectProcessed(PageHelper<CloudRecord> pageHelper);

    /**
     * 查询单个外出报备信息详情
     * @param recordId
     * @return
     */
    CloudRecordVo selectOne(Long recordId);

    /**
     * 根据实体类中不为空的字段查询
     * @param cloudRecord
     * @return
     */
    List<CloudRecord> selectByCondition(CloudRecord cloudRecord);

    /**
     * 审核完成后更改外出报备数据的状态
     * @param cloudRecord
     * @return
     */
    boolean updateStatus(CloudRecord cloudRecord);

    /**
     * 逻辑删除外出报备信息
     * @param recordId
     * @return
     */
    boolean delete(Long recordId);
}
