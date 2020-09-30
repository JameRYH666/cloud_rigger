package com.jeeadmin.vo.meeting;

import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.entity.CloudMeeting;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author Seven Lee
 * @Date Create in 2020/9/12 15:44
 * @Description
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudMeetingDetailVo extends CloudMeeting {
    private CloudMeeting cloudMeeting;
    @ApiModelProperty(value = "会议形式")
    private String formName;
    @ApiModelProperty(value = "会议类型")
    private String typeName;
    /**
     * 组织人员名称
     */
    @ApiModelProperty(value = "组织人员名称")
    private String memberName;
    @ApiModelProperty(value = "创建时间")
    private Date oneCreateDate;
    /**
     * 参会人员名称
     */
    @ApiModelProperty(value = "参会人员名称")
    private List<CloudMeetingPartyMemberVo> joinMember;
    @ApiModelProperty(value = "发起人名称")
    private List<CloudMeetingPartyMemberVo> meetingSponsor;
    /**
     * 附件信息
     */
    @ApiModelProperty(value = "附件信息")
    private List<CloudEnclosure> enclosureList;

}
