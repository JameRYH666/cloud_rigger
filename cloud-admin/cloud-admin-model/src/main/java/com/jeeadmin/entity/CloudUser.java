package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @ApiModelProperty(value = "登陆名")
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
    @ApiModelProperty(value = "密码盐值")
    private String userSalt;

    /**
     * 显示顺序（升序）
     */
    @ApiModelProperty(value = "显示顺序（升序）")
    private Integer userSort;

    /**
     * 备注信息(冗余字段)
     */
    @ApiModelProperty(value = "备注信息(冗余字段)")
    private String remark;

    /**
     * 管理员类型（0：超级管理员 1：系统管理员）
     */
    @ApiModelProperty(value = "管理员类型（0：超级管理员 1：系统管理员）")
    private String mgrType;

    /**
     * 用户状态（0:正常 2:停用 3:冻结）
     */
    @Pattern(regexp = "[023]",message = "用户状态值必须为0或2或3（0：正常 2：停用 3：冻结）！")
    @ApiModelProperty(value = "用户状态（0:正常 2:停用 3:冻结）")
    private String userStatus;

    /**
     *      是否为管理员(1：是，2：不是)
     */
    @Pattern(regexp = "[12]",message = "是否为管理员(1：是，2：不是)")
    @ApiModelProperty(value = "是否为管理员(1：是，2：不是)")
    private String adminFlag;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private Long enclosureId;

    /**
     *  用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     *  用户学历
     */
    @ApiModelProperty(value = "用户学历")
    private String educationCode;

    /**
     *  用户籍贯
     */
    @ApiModelProperty(value = "用户籍贯")
    private String hometown;

    /**
     *  用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /**
     *  用户年龄
     */
    @ApiModelProperty(value = "用户年龄")
    private Integer age;

    /**
     *  用户民族
     */
    @ApiModelProperty(value = "用户民族")
    private String nationCode;

    /**
     *  用户联系电话
     */
    @ApiModelProperty(value = "用户联系电话")
    private Long phoneNumber;

    /**
     *  用户生日
     */
    @ApiModelProperty(value = "用户生日")
    private Date birthday;

    /**
     *  用户证件号码
     */
    @ApiModelProperty(value = "用户证件号码")
    private String certificateNumber;

    /**
     *  用户证件类型
     */
    @ApiModelProperty(value = "用户证件类型")
    private String certificateCode;

    /**
     *  用户性别
     */
    @ApiModelProperty(value = "用户性别")
    private String sexCode;

    /**
     *  用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String realName;

    @TableField(exist = false)
    @ApiModelProperty(value = "组织机构ID")
    private Long orgId;



}