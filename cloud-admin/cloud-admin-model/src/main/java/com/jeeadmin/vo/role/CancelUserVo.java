package com.jeeadmin.vo.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 批量取消角色已分配的用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CancelUserVo {
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    Long roleId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    List<Long> userIdList;
}
