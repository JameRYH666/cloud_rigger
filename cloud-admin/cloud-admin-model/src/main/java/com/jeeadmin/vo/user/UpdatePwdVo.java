package com.jeeadmin.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UpdatePwdVo {
    /**
     * 老密码
     */
    @ApiModelProperty(value = "老密码")
    private String oldPassword;
    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码")
    private String newPassword;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /**
     * 手机短信验证码
     */
    @ApiModelProperty(value = "手机短信验证码")
    private String imgCode;
}
