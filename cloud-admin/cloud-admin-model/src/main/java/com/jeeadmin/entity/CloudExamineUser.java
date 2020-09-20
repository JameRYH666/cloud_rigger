package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
* @Author: Ryh
* @Description:         用户审核关系表
* @Param: [null]
* @Date: Create in 2020/9/19
* @Return: null
* @Throws:
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudExamineUser extends BaseModel<CloudExamineUser> {

    /**
     *  审核用户ID
     */
    private Long userId;

    /**
     *  审核ID
     */
    private Long examineId;

    private Date createDate;
}