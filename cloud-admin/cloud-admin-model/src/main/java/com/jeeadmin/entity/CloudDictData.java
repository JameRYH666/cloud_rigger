package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Seven Lee
 * @description
 *      字典数据实体
 * @date 2020/9/8
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudDictData extends BaseModel<CloudDictData> {

    /**
     * 上级字典数据id
     */
    private Long parentId;

    /**
     * 字典类型
     */
    @NotNull(message = "字典类型不能为空！")
    private String dictType;

    /**
     * 字典编码
     */
    private String dataCode;

    /**
     * 字典标签
     */
    @NotNull(message = "字典标签不能为空！")
    @Size(max = 64, message = "字典标签长度最大值为64！")
    private String dictLabel;

    /**
     * 字典键值
     */
    @NotNull(message = "字典键值不能为空！")
    @Size(max = 10, message = "字典键值长度最大值为10！")
    private String dictValue;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典状态（0:正常  2:停用）
     */
    @Pattern(regexp = "[02]", message = "字典状态值必须为0或2（0：正常 2：停用）！")
    private String dictStatus;

    /**
     * 是否叶子节点（0:否 1:是）
     */
    @Pattern(regexp = "[01]", message = "系统内置标识值必须为0或1（0:否 1:是）！")
    private String leafFlag;

    /**
     * 是否系统内置（0:否 1:是）
     */
    private String sysFlag;

    /**
     * 归属系统标识
     */
    private String sign;

    /**
     * 默认字段
     */
    private String def;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 字典描述
     */
    private String dictDesc;
}