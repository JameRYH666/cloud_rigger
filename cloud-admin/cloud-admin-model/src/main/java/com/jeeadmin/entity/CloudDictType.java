package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Seven Lee
 * @description
 *      字典类型实体
 * @date 2020/9/8
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudDictType extends BaseModel<CloudDictType> {

    /**
     * 字典类型
     */
    @NotNull(message = "字典类型不能为空！")
    @Size(max = 64, message = "字典类型长度最大值为64！")
    private String dictType;

    /**
     * 字典类型名称
     */
    @NotNull(message = "字典名称不能为空！")
    @Size(max = 64, message = "字典名称长度最大值为64！")
    private String dictName;

    /**
     * 使用状态（0:正常  2:停用）
     */
    @Pattern(regexp = "[02]", message = "字典状态值必须为0或1或2（0：正常 2：停用）！")
    private String dictStatus;

    /**
     * 系统字典标识(0:否 1:是)
     */
    @Pattern(regexp = "[01]", message = "系统字典标识值必须为0或1（0:否 1:是）！")
    private String sysFlag;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 归属系统标识
     */
    private String sign;

    /**
     * 分类id
     */
    private Long dictCat;
}