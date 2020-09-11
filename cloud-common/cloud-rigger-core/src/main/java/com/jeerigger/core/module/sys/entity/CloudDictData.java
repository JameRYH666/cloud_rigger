package com.jeerigger.core.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author wangcy
 * @since 2018-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_dict_data")
public class CloudDictData extends BaseModel<CloudDictData> {

    private static final long serialVersionUID = 1L;

    /**
     * 上级字典数据id
     */
    private String parentId;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 显示顺序
     */
    private Integer dictSort;

}
