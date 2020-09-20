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
 *      系统参数配置实体
 * @param
 * @date 2020/9/8
 * @return
 * @throws
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudParam extends BaseModel<CloudParam> {

    /**
     * 参数配置名称
     */
    @NotNull(message = "参数名称不能为空！")
    @Size(max = 64,message = "参数名称长度最大值为64！")
    private String paramName;

    /**
     * 参数配置键名
     */
    @NotNull(message = "参数键名不能为空！")
    @Size(max = 64,message = "参数键名长度最大值为64！")
    private String paramKey;

    /**
     * 参数配置键值
     */
    @NotNull(message = "参数键值不能为空！")
    @Size(max = 200,message = "参数键值长度最大值为200！")
    private String paramValue;

    /**
     * 系统参数标识(0:否 1:是)
     */
    @Pattern(regexp = "[01]",message = "系统内置标识值必须为0或1（0:否 1:是）！")
    private String sysFlag;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    private Date createDate;
}