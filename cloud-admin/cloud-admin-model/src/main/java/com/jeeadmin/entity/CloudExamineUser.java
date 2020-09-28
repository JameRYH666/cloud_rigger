package com.jeeadmin.entity;

import com.jeerigger.frame.base.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("用户审核关系表")
public class CloudExamineUser extends BaseModel<CloudExamineUser> {

    /**
     *  审核用户ID(党员id)
     */
    @ApiModelProperty("审核用户ID(党员id)")
    private Long partyMemberId;

    /**
     *  审核ID
     */
    @ApiModelProperty("审核ID")
    private Long examineId;
}