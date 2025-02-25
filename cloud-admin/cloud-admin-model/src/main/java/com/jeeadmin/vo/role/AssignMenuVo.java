package com.jeeadmin.vo.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AssignMenuVo {
    /**
     * 角色uuid
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    /**
     * 角色添加用户
     */
    @ApiModelProperty(value = "角色添加用户")
    List<Long> menuIdList;
}
