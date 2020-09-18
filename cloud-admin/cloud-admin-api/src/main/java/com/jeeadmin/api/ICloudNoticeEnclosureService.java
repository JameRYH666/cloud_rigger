package com.jeeadmin.api;

import com.jeeadmin.entity.CloudNoticeEnclosure;
import com.jeerigger.frame.base.service.BaseService;

public interface ICloudNoticeEnclosureService extends BaseService<CloudNoticeEnclosure> {


    /**
     *  新增公告和附件的关系
     */
    CloudNoticeEnclosure saveNoticeEnclosure(CloudNoticeEnclosure cloudNoticeEnclosure);





}
