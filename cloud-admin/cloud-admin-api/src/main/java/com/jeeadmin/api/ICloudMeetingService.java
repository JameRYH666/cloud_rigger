package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.jeeadmin.entity.CloudMeeting;
import com.jeeadmin.vo.meeting.CloudMeetingDetailVo;
import com.jeeadmin.vo.meeting.CloudMeetingVo;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;


/**
 * @author: Sgz
 * @time: 2020/9/11 16:26
 * @description:
 *  会议信息表接口
 */

public interface ICloudMeetingService extends BaseService<CloudMeeting> {
    /**
     * @Author: Sgz
     * @Time: 16:56 2020/9/11
     * @Params: [pageHelper]
     * @Return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jeeadmin.entity.CloudMeeting>
     * @Throws:
     * @Description:
     *  查询所有的会议信息，并进行分页处理
     *
     */
    Page<CloudMeetingVo> selectPage(PageHelper<CloudMeetingVo> pageHelper);
    /**
     * @Author: Sgz
     * @Time: 17:04 2020/9/11
     * @Params: [id]
     * @Return: com.jeeadmin.entity.CloudMeeting
     * @Throws:
     * @Description:
     * 根据会议id查询单个会议信息
     *
     */
    CloudMeetingDetailVo selectOneMeeting(Long id);

    /**
     * @Author: Sgz
     * @Time: 17:04 2020/9/11
     * @Params: [cloudMeeting]
     * @Return: com.jeeadmin.entity.CloudMeeting
     * @Throws:
     * @Description:
     *  新增会议信息
     *
     */
    boolean  saveMeeting(CloudMeetingVo cloudMeeting);

    /**
     * @Author: Sgz
     * @Time: 17:04 2020/9/11
     * @Params: [cloudMeeting]
     * @Return: boolean
     * @Throws:
     * @Description:
     * 更新会议信息
     *
     */
    boolean updateMeeting(CloudMeetingVo cloudMeeting);

    /**
     * @Author: Sgz
     * @Time: 17:05 2020/9/11
     * @Params: [id]
     * @Return: boolean
     * @Throws:
     * @Description:
     *  根据会议id
     *
     */
    boolean deleteMeeting(Long id);

    /**
     * 新增会议信息
     * @param cloudMeeting
     * @return
     */

    boolean saveOne(CloudMeeting cloudMeeting);

    /**
     * 更新会议状态
     * @param cloudMeeting
     * @return
     */

    boolean updateStatus(CloudMeeting cloudMeeting);





}
