package com.jeeadmin.api;

import com.jeeadmin.entity.CloudDictData;
import com.jeerigger.frame.base.service.BaseService;

import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      字典数据表 服务类
 * @date 2020/9/8
**/
public interface ICloudDictDataService extends BaseService<CloudDictData> {
    /**
     * 查询字典数据列表
     *
     * @param sysDictData 查询条件
     * @return
     */
    List<CloudDictData> selectDictDataList(CloudDictData sysDictData);

    /**
     * 更新字典数据
     *
     * @param dictDataId 字典数据主键
     * @param dictStatus 状态 0:正常 1:删除 2:停用
     * @return
     */
    boolean updateStatus(Long dictDataId, String dictStatus);

    /**
     * 新增字典数据
     *
     * @param sysDictData
     * @return
     */
    CloudDictData saveDictData(CloudDictData sysDictData);

    /**
     * 更新字典数据
     *
     * @param sysDictData
     * @return
     */
    boolean updateDictData(CloudDictData sysDictData);

    /**
     * 更新字典数据
     *
     * @param dictDataId 字典数据唯一标识
     * @return
     */
    boolean deleteDictData(Long dictDataId);


}
