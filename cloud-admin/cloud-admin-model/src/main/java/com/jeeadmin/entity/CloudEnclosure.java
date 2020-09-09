package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author Seven Lee
 * @description
 *      附件实体
 * @date 2020/9/8
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudEnclosure extends BaseModel<CloudEnclosure> {

    /**
     * 文件MD5码
     */
    private String md5Code;

    /**
     * 文件类型
     */
    private String enclosureType;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 文件地址
     */
    private String enclosurePath;

    /**
     * 文件名称
     */
    private String enclosureName;

}