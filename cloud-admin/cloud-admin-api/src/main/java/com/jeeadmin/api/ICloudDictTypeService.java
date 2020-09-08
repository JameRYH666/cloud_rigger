package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeeadmin.entity.CloudDictType;
import com.jeerigger.frame.page.PageHelper;

/**
 * @author Seven Lee
 * @description
 *      字典类型表 服务类
 * @date 2020/9/8
**/
public interface ICloudDictTypeService extends IService<CloudDictType> {
    /**
     * 查询字典类型数据
     *
     * @param pageHelper
     * @return
     */
    Page<CloudDictType> selectPage(PageHelper<CloudDictType> pageHelper);

    /**
     * 更新字典类型状态
     *
     * @param sysDictType
     * @return
     */
    boolean updateStatus(CloudDictType sysDictType);

    /**
     * 新增字典类型
     *
     * @param sysDictType
     * @return
     */
    boolean saveDictType(CloudDictType sysDictType);

    /**
     * 更新字典类型
     *
     * @param sysDictType
     * @return
     */
    boolean updateDictType(CloudDictType sysDictType);

    /**
     * 删除字典类型
     *
     * @param dictId 字典类型UUID
     * @return
     */
    boolean deleteDictType(Long dictId);

}
