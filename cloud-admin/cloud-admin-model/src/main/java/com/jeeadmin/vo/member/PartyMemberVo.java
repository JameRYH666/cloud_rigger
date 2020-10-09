package com.jeeadmin.vo.member;

import com.jeeadmin.entity.CloudPartyMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @projectName: jeerigger-starter-parent
 * @packageName: com.jeeadmin.vo.member
 * @description:
 * @author: huayang.bai
 * @date: 2020-09-2020/9/12 13:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PartyMemberVo extends CloudPartyMember {
    /**
     * 党员姓名
     */
    @ApiModelProperty(value = "党员姓名")
    private String memberName;
    /**
     * 党员性别
     */
    @ApiModelProperty(value = "党员性别")
    private String sexCode;
    /**
     * 党员年龄
     */
    @ApiModelProperty(value = "党员年龄")
    private Integer memberAge;
    /**
     * 党内职务
     */
    @ApiModelProperty(value = "党内职务")
    private String jobCode;

    /**
     * 党员电话
     */
    @ApiModelProperty(value = "党员电话")
    private Long memberPhoneNumber;
    /**
     * 所属党支部
     */
    @ApiModelProperty(value = "所属党支部ID")
    private Long orgId;
    /**
     * 党龄
     */
    @ApiModelProperty(value = "党龄")
    private Integer partyTime;
    /**
     * 模范类型
     */
    @ApiModelProperty(value = "模范类型")
    private String modelCode;
    /**
     * 党员历程
     */
    @ApiModelProperty(value = "党员历程")
    private String memberProgress;
    /**
     * 党员状态（0:正常 2:停用 3:冻结）
     */
    @ApiModelProperty(value = "党员状态（0:正常 2:停用 3:冻结）")
    private String memberStatus;
    /**
     * 党员邮箱
     */
    @ApiModelProperty(value = "党员邮箱")
    private String memberEmail;

}
