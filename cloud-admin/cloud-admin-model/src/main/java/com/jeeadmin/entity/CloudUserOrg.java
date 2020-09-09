package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Seven Lee
 * @description
 *      管理员组织机构关系表
 * @param
 * @date 2020/9/8
 * @return
 * @throws
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_user_org")
public class CloudUserOrg extends BaseModel<CloudUserOrg> {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 组织机构id
     */
    private Long orgId;

    /**
     * 创建用户
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新用户
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private Date updateDate;

}