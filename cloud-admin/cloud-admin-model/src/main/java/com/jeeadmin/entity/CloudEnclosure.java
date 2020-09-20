package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import java.util.Date;


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
    /**
     *  附件状态(1：正常，2：删除)
     */
    @Pattern(regexp = "[12]",message = "附件状态必须为1或者2")
    private String enclosureStatus;


    private Date createDate;
}