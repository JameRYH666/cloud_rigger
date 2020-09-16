package com.jeeadmin.api;

import com.jeeadmin.entity.CloudEnclosure;
import com.jeerigger.frame.base.service.BaseService;

public interface ICloudEnclosureSerivce extends BaseService<CloudEnclosure> {





    /**
    * @Author: sgz
    * @Description:     删除附件信息
    * @Param: [meetingId]
    * @Date: Create in 2020/9/13
    * @Return: boolean
    * @Throws:
    */
    boolean deleteEnclosure(Long meetingId);
    /**
     * @Author: Sgz
     * @Time: 15:19 2020/9/14
     * @Params: [cloudEnclosure]
     * @Return: boolean
     * @Throws:
     * @Description:
     * 新增附件信息
     *
     */
    boolean saveEnclosure(CloudEnclosure cloudEnclosure);


}
