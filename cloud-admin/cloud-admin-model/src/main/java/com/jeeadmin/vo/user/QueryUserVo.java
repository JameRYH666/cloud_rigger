package com.jeeadmin.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QueryUserVo {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String loginName;

    /**
     * 联系手机
     */
    @ApiModelProperty(value = "联系手机")
    private String userMobile;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String userEmail;

    /**
     * 组织机构ID
     */
    @ApiModelProperty(value = "组织机构ID")
    private Long orgId;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private String userStatus;
}
