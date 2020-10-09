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
* @Description:
 *              外出报备表的实体
* @Param: [null]
* @Date: Create in 2020/9/18
* @Return: null
* @Throws:
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("外出报备表")
public class CloudRecord extends BaseModel<CloudRecord> {

    /**
     *  外出时间
     */
    @ApiModelProperty("外出时间")
    private Date recordStartTime;

    /**
     *  结束时间
     */
    @ApiModelProperty("结束时间")
    private Date recordEndTime;

    /**
     *  外出天数
     */
    @ApiModelProperty("外出天数")
    private Integer recordDay;

    /**
     *  外出地址
     */
    @ApiModelProperty("外出地址")
    private String recordAddress;

    /**
     *  同行人员
     */
    @ApiModelProperty("同行人员")
    private String recordWithPerson;

    /**
     *  外出联系人
     */
    @ApiModelProperty("外出联系人")
    private String recordContact;

    /**
     *  外出联系人电话
     */
    @ApiModelProperty("外出联系人电话")
    private String recordContactPhoneNumber;

    /**
     *  外出代主持工作人
     */
    @ApiModelProperty("外出代主持工作人")
    private String recordJobContact;

    /**
     *  外出代主持工作人电话
     */
    @ApiModelProperty("外出代主持工作人电话")
    private String recordJobContactPhoneNumber;

    /**
     *  报备类型
     */
    @ApiModelProperty("报备类型")
    private String typeCode;

    /**
     *  外出发起人
     */
    @ApiModelProperty("外出发起人id")
    private Long partyMemberId;

    /**
     *  报备状态
     */
    @ApiModelProperty("报备状态")
    private String recordStatus;

    @ApiModelProperty("承诺时间")
    private Date recordCommittedTime;

    @ApiModelProperty("证件类型")
    private String recordCertificateCode;

    @ApiModelProperty("证件号码")
    private String recordCertificateNumber;

    @ApiModelProperty("有效开始时间")
    private Date recordValidityBegins;

    @ApiModelProperty("有效结束时间")
    private Date recordValidityEnds;

    @ApiModelProperty("婚事/丧事时间")
    private Date recordDate;

    @ApiModelProperty("当事人姓名")
    private String recordPartyName;

    @ApiModelProperty("关系类型")
    private String recordRelationshipCode;

    @ApiModelProperty("操办方式")
    private String recordOperationMethod;

    @ApiModelProperty("操办规模")
    private String recordOperationalScale;

    @ApiModelProperty("邀请人数")
    private Integer recordGuestsNumber;

    @ApiModelProperty("参加人数")
    private Integer recordParticipateNumber;

    @ApiModelProperty("使用车辆数量")
    private Integer recordCarNumber;

    @ApiModelProperty("设席数量")
    private Integer recordSeatNumber;

    @ApiModelProperty("花费")
    private Long recordSpend;

    @ApiModelProperty("接受礼金")
    private Long recordAcceptMoney;

    @ApiModelProperty("接受礼品")
    private Integer recordAcceptGifts;

    /**
     *      备注信息
     */
    @ApiModelProperty("备注信息")
    private String remark;

    /**
     *  外出事由
     */
    @ApiModelProperty("外出事由")
    private String recordReason;

}