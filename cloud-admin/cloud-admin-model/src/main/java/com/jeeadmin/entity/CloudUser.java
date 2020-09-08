package com.jeeadmin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeerigger.frame.base.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
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
 *      管理员实体
 * @param
 * @date 2020/9/8
 * @return
 * @throws
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudUser extends BaseModel<CloudUser> {

    /**
     * 登陆名
     */
    @NotNull(message="登录名不能为空！")
    @Size(min =4,max = 64 ,message = "登录名长度最小4最大64！")
    private String loginName;

    /**
     * 密码
     */
    @ApiModelProperty(
            hidden = true
    )
    @JsonIgnore
    private String password;

    /**
     * 密码盐值
     */
    @NotNull(message="密码盐值不能为空！")
    private String userSalt;

    /**
     * 显示顺序（升序）
     */
    private Integer userSort;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 管理员类型（0：超级管理员 1：系统管理员）
     */
    private String mgrType;

    /**
     * 用户状态（0:正常 2:停用 3:冻结）
     */
    @Pattern(regexp = "[023]",message = "用户状态值必须为0或2或3（0：正常 2：停用 3：冻结）！")
    private String userStatus;
}