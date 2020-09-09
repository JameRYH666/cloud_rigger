package com.jeeadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Seven Lee
 * @description
 *      党员实体
 * @date 2020/9/8
**/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudPartyMember extends BaseModel<CloudPartyMember> {

    /**
     * 党员姓名
     */
    private String memberName;

    /**
     * 党员照片
     */
    private Long enclosureId;

    /**
     * 党员性别
     */
    private String sexCode;

    /**
     * 党员年龄
     */
    private Integer memberAge;

    /**
     * 党内职务
     */
    private String jobCode;

    /**
     * 党员电话
     */
    private Long memberPhoneNumber;

    /**
     * 党员简介
     */
    private String memberDesc;

    /**
     * 所属党支部
     */
    private Long orgId;


    @TableField(exist = false)
    private String orgName;

    /**
     * 入党时间
     */
    private Date memberEnterTime;

    /**
     * 党龄
     */
    private Integer partyTime;

    /**
     * 模范类型
     */
    private String modelCode;

    /**
     * 党员历程
     */
    private String memberProgress;

    /**
     * 备注信息(冗余字段)
     */
    private String remark;

    /**
     * 党员状态（0:正常 2:停用 3:冻结）
     */
    private String memeberStatus;

    /**
     * 党员邮箱
     */
    private String memberEmail;

}