package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Seven Lee
 * @description
 *      角色菜单关系表
 * @param
 * @date 2020/9/8
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_role_menu")
public class CloudRoleMenu extends BaseModel<CloudRoleMenu> {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

  /*  *//**
     * 创建用户
     *//*
    private Long createUser;

    *//**
     * 创建时间
     *//*
    private Date createDate;

    *//**
     * 修改用户
     *//*
    private Long updateUser;

    *//**
     * 修改时间
     *//*
    private Date updateDate;*/

}