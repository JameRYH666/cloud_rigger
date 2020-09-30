package com.jeeadmin.vo.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色分配用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AssignUserVo {
    /**
     * 角色uuid
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    /**
     * 角色添加用户
     */
    @ApiModelProperty(value = "角色添加用户")
    List<Long> userIdList;
}
