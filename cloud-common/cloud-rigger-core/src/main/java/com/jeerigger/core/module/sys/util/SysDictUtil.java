package com.jeerigger.core.module.sys.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeerigger.core.module.sys.entity.CloudDictData;
import com.jeerigger.core.module.sys.mapper.DictDataMapper;
import com.jeerigger.frame.enums.StatusEnum;
import com.jeerigger.frame.support.util.SpringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * 考虑未来从缓存里获取
 */

public class SysDictUtil {

    private static final String SYS_DICT_DATA_LIST = "org_dict_data";

    private static Map<String, CloudDictData> dictDataMap = new HashMap<>();

    private static DictDataMapper getDictDataMapper() {
        return SpringUtil.getBean(DictDataMapper.class);
    }

    /**
     * 根据字段类型获取字段数据列表
     *
     * @param dictType
     * @return
     */
    public static List<CloudDictData> getSysDictDataList(String dictType) {
        QueryWrapper<CloudDictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", dictType);
        //只获取状态为正常的数据
        queryWrapper.eq("dict_status", StatusEnum.NORMAL.getCode());
        queryWrapper.orderByAsc("dict_sort");
        return getDictDataMapper().selectList(queryWrapper);
    }

    /**
     * 根据字典类型，字典键值获取字段标签
     *
     * @param dictType
     * @param dictValue
     * @return
     */
    public static String getDictLable(String dictType, String dictValue) {
        return getDictLable(dictType, dictValue, "");
    }

    /**
     * 根据字典类型，字典键值获取字段标签
     *
     * @param dictType
     * @param dictValue
     * @param defaultLable
     * @return
     */
    public static String getDictLable(String dictType, String dictValue, String defaultLable) {
        CloudDictData sysDictData = getSysDictData(dictType, dictValue);
        if (sysDictData != null) {
            return sysDictData.getDictLabel();
        } else {
            return defaultLable;
        }
    }

    public static CloudDictData getSysDictData(String dictType, String dictValue) {
        CloudDictData dictData = dictDataMap.get(dictType + dictValue);
        if (dictData == null) {
            QueryWrapper<CloudDictData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dict_type", dictType);
            queryWrapper.eq("dict_value", dictValue);
            dictData = getDictDataMapper().selectOne(queryWrapper);
            dictDataMap.put(dictType + dictValue, dictData);
        }
        return dictData;
    }

}
