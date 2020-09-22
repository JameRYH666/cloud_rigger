package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import com.jeerigger.frame.base.model.BaseTreeModel;
import io.swagger.annotations.ApiModelProperty;
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
 *      组织机构实体
 * @param
 * @date 2020/9/8
 * @return
 * @throws
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_org")
public class CloudOrg extends BaseTreeModel<CloudOrg> {

    /**
     * 上级组织机构id
     */
    private Long parentId;

    /**
     * 组织机构代码
     */
    @NotNull(message = "组织机构代码不能为空！")
    @Size(max = 64,message = "组织机构代码最大长度为64！")
    private String orgCode;

    /**
     * 组织机构类型
     */
    @NotNull(message = "组织机构类型不能为空！")
    private String orgTypeCode;


    @TableField(exist = false)
    private String orgTypeName;

    /**
     * 组织机构名称
     */
    @NotNull(message = "组织机构名称不能为空！")
    @Size(max = 64,message = "组织机构名称最大长度为64！")
    private String orgName;

    /**
     * 组织机构简称
     */
    @NotNull(message = "组织机构简称不能为空！")
    @Size(max = 64,message = "组织机构简称最大长度为64！")
    private String orgShortName;

    /**
     * 组织机构负责人(党员id)
     */
    @Size(max = 64,message = "组织机构负责人最大长度为64！")
    private Long orgPartyMemberId;

    /**
     * 显示顺序
     */
    private Integer orgSort;

    /**
     * 使用状态（0:正常 2:停用）
     */
    @Pattern(regexp = "[02]",message = "系统内置标识值必须为0或2（0:正常 2:停用）！")
    private String orgStatus;

    /**
     * 是否叶子节点（0:否 1:是）
     */
    private String leafFlag;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 顶级机构
     */
    private String topLevel;

    /**
     * 上级组织机构信息
     */
    @ApiModelProperty(
            hidden = true
    )
    @TableField(exist = false)
    private CloudOrg  parentOrg;
}