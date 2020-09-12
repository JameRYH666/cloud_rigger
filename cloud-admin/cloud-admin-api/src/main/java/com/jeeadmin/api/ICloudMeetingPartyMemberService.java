package com.jeeadmin.api;

import com.jeeadmin.entity.CloudMeetingEnclosure;
import com.jeeadmin.entity.CloudMeetingPartyMember;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

import java.util.List;

/**
 * @author: Sgz
 * @time: 2020/9/12 9:58
 * @description:
 * 会议参会人员表接口
 */
public interface ICloudMeetingPartyMemberService extends BaseService<CloudMeetingPartyMember> {
    /**
     * @Author: Sgz
     * @Time: 10:02 2020/9/12
     * @Params: [pageHelper]
     * @Return: java.util.List<com.jeeadmin.entity.CloudMeetingPartyMember>
     * @Throws:
     * @Description:
     *  查询所有的会议参与人员，并通过分页进行展示数据
     *
     */
    List<CloudMeetingPartyMember> selectPage(PageHelper<CloudMeetingPartyMember> pageHelper);


    /**
     * @Author: Sgz
     * @Time: 10:21 2020/9/12
     * @Params: [cloudMeetingPartyMember]
     * @Return: com.jeeadmin.entity.CloudMeetingPartyMember
     * @Throws:
     * @Description:
     *  新增会议参会人员
     */

    CloudMeetingPartyMember saveMeetingPartyMember(CloudMeetingPartyMember cloudMeetingPartyMember);
    /**
     * @Author: Sgz
     * @Time: 10:23 2020/9/12
     * @Params: [cloudMeetingPartyMember]
     * @Return: boolean
     * @Throws:
     * @Description:
     *  修改会议参会人员
     */
    boolean updateMeetingPartyMember(CloudMeetingPartyMember cloudMeetingPartyMember);
    /**
     * @Author: Sgz
     * @Time: 10:24 2020/9/12
     * @Params: [id]
     * @Return: boolean
     * @Throws:
     * @Description:
     *      通过附件id删除参会人员
     *
     */
    boolean deleteMeetingPartyMember(Long id);





}
