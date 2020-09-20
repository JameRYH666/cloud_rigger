package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jeerigger.frame.base.model.BaseModel;
import com.jeerigger.frame.base.model.BaseTreeModel;
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
 *      菜单实体
 * @param
 * @date 2020/9/8
 * @return
 * @throws
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudMenu extends BaseTreeModel<CloudMenu> {

    /**
     * 上级菜单id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotNull(message = "菜单名称不能为空!")
    @Size(max = 150, message = "菜单名称最大长度为150!")
    private String menuName;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 菜单权重
     */
    @TableField(exist = false)
    private String menuWeightName;

    /**
     * 菜单地址
     */
    @NotNull(message = "菜单地址不能为空!")
    @Size(max = 100, message = "菜单地址最大长度为100!")
    private String menuHref;

    /**
     * 菜单图标
     */
    @Size(max = 100, message = "菜单图标地址最大长度为100!")
    private String menuIcon;

    /**
     * 显示顺序
     */
    private Integer menuSort;

    /**
     * 菜单权重
     */
    private Integer menuWeight;

    /**
     * 菜单颜色
     */
    @Size(max = 100, message = "菜单颜色最大长度为100!")
    private String menuColor;

    /**
     * 菜单打开方式
     */
    @NotNull(message = "菜单打开方式不能为空!")
    @Size(max = 1, message = "菜单打开方法最大为1!")
    private String menuTarget;

    /**
     * 归属系统（后台系统、App系统）
     */
    @NotNull(message = "菜单归属系统不能为空!")
    private String sysCode;

    /**
     * 权限标识
     */
    @Size(max = 64, message = "菜单权限标识最大长度为64!")
    private String permission;

    /**
     * 是否显示（1:显示 0:隐藏）
     */
    @Pattern(regexp = "[01]", message = "菜单是否显示值必须为0或1 ( 0:隐藏 1:显示)!")
    @Size(max = 1, message = "菜单是否显示最大长度为1!")
    private String showFlag;

    /**
     * 是否叶子节点（0:否 1:是）
     */
    private String leafFlag;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    private Date createDate;

}