package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jeerigger.frame.base.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Seven Lee
 * @description
 *      角色实体
 * @date 2020/9/8
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudRole extends BaseModel<CloudRole> {

    /**
     * 角色编码
     */
    @NotNull(message = "角色编码不能为空！")
    @Size(max = 64, message = "角色编码长度最大值为64！")
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotNull(message = "角色名称不能为空！")
    @Size(max = 64, message = "角色名称长度最大值为64！")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色排序
     */
    @ApiModelProperty(value = "角色排序")
    private Integer roleSort;

    /**
     * 角色类型
     */
    @Size(max = 64, message = "角色类型长度最大值为64！")
    @ApiModelProperty(value = "角色类型")
    private String roleType;

    /**
     * 角色类型名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "角色类型名称")
    private String roleTypeName;

    /**
     * 角色状态（0:正常 2:停用）
     */
    @Pattern(regexp = "[02]", message = "角色状态值必须为0或1或2（0：正常 2：停用）！")
    @ApiModelProperty(value = "角色状态（0:正常 2:停用）")
    private String roleStatus;

    /**
     * 系统内置（0:否 1:是 ）
     */
    @Pattern(regexp = "[01]", message = "系统字典标识值必须为0或1（0:否 1:是）！")
    @ApiModelProperty(value = "系统内置（0:否 1:是 ）")
    private String sysFlag;

    /**
     * 备注信息(冗余字段)
     */
    @ApiModelProperty(value = "备注信息(冗余字段)")
    private String remark;

    /**
     * 角色分配菜单使用
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "角色分配菜单使用")
    private List<Long> menuIdList;

}