package com.jeeadmin.api;

import com.jeeadmin.entity.CloudMeetingEnclosure;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

import java.util.List;

/**
 * @author: Sgz
 * @time: 2020/9/12 9:58
 * @description:
 * 会议记录表接口
 */
public interface ICloudMeetingEnclosureService extends BaseService<CloudMeetingEnclosure> {
    /**
     * @Author: Sgz
     * @Time: 10:02 2020/9/12
     * @Params: []
     * @Return: java.util.List<com.jeeadmin.entity.CloudMeetingEnclosure>
     * @Throws:
     * @Description:
     *  查询所有的会议附件
     *
     */
    List<CloudMeetingEnclosure> selectAll();
    /**
     * @Author: Sgz
     * @Time: 10:10 2020/9/12
     * @Params: [id]
     * @Return: com.jeeadmin.entity.CloudMeetingEnclosure
     * @Throws:
     * @Description:
     *  通过会议附件id查询会议附件的详细信息
     *
     */
    CloudMeetingEnclosure getMeetingEnclosureDetailByEnclosureId(Long id);

    /**
     * @Author: Sgz
     * @Time: 10:21 2020/9/12
     * @Params: [cloudMeetingEnclosure]
     * @Return: com.jeeadmin.entity.CloudMeetingEnclosure
     * @Throws:
     * @Description:
     *  新增会议附件
     */

    CloudMeetingEnclosure saveMeetingEnclosure(CloudMeetingEnclosure cloudMeetingEnclosure);
    /**
     * @Author: Sgz
     * @Time: 10:23 2020/9/12
     * @Params: [cloudMeetingEnclosure]
     * @Return: boolean
     * @Throws:
     * @Description:
     *  修改会议附件信息
     */
    boolean updateMeetingEnclosure(CloudMeetingEnclosure cloudMeetingEnclosure);
    /**
     * @Author: Sgz
     * @Time: 10:24 2020/9/12
     * @Params: [id]
     * @Return: boolean
     * @Throws:
     * @Description:
     *      通过附件id删除附件信息
     *
     */
    boolean deleteMeetingEnclosure(Long id);



}
